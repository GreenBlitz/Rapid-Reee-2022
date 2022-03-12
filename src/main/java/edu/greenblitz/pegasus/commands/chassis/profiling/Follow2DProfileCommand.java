package edu.greenblitz.pegasus.commands.chassis.profiling;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.gblib.threading.IThreadable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.base.Vector2D;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ChassisProfiler2D;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.ProfilingConfiguration;
import org.greenblitz.motion.profiling.ProfilingData;
import org.greenblitz.motion.profiling.followers.PidFollower2D;
import org.greenblitz.motion.profiling.kinematics.CurvatureConverter;

import java.util.List;

public class Follow2DProfileCommand implements IThreadable {

	private MotionProfile2D profile2D;
	private PidFollower2D follower;
	private double linKv, linKa;
	private PIDObject perWheelPIDConsts;
	private PIDObject angularPIDConsts;
	private double collapsingPerWheelPIDTol;
	private double collapsingAngularPIDTol;

	private double maxPower;
	private boolean died;
	private boolean isOpp;

	private double mult;

	private long runTStart;
	private long minRuntime = 8;

	private double startV = 0;
	private double endV = Double.POSITIVE_INFINITY;

	private Boolean sendData;


	public Follow2DProfileCommand(
			List<State> path,
			ProfilingConfiguration config,
			double maxPower,
			boolean isReverse) {
		this(path, config,
				(RobotMap.Pegasus.Chassis.MotionData.PROF.getValue().containsKey(Double.toString(maxPower))) ?
						RobotMap.Pegasus.Chassis.MotionData.PROF.getValue().get(Double.toString(maxPower)) : retNullandThrow(), maxPower, isReverse);
	}


	public Follow2DProfileCommand(List<State> path,
	                              ProfilingConfiguration config,
	                              ProfilingData data,
	                              double maxPower,
	                              boolean isReverse) {
		this(path,
				config.getJump(),
				config.getSmoothingTail(),
				data,
				1.0,
				maxPower*config.getVelMultLin(),
				maxPower*config.getAccMultLin(),
				new PIDObject(
						config.getWheelPidKp(),
						config.getWheelPidKi(),
						config.getWheelPidKd()),
				config.getCollapseConstPerWheel(),
				new PIDObject(
						config.getAngPidKp(),
						config.getAngPidKi(),
						config.getAngPidKd()),
				config.getCollapseConstAngular(),
				isReverse);
	}

	/**
	 * @param path
	 * @param jump
	 * @param smoothingTail
	 * @param data
	 * @param maxPower
	 * @param velMultLin
	 * @param accMultLin
	 * @param perWheelPIDCosnts
	 * @param collapseConstaPerWheel
	 * @param angularPIDConsts
	 * @param collapseConstAngular
	 * @param isReverse
	 */
	public Follow2DProfileCommand(List<State> path,
	                              double jump,
	                              int smoothingTail,
	                              ProfilingData data,
	                              double maxPower,
	                              double velMultLin,
	                              double accMultLin,
	                              PIDObject perWheelPIDCosnts,
	                              double collapseConstaPerWheel,
	                              PIDObject angularPIDConsts,
	                              double collapseConstAngular,
	                              boolean isReverse) {
		this(path, jump, smoothingTail, data, maxPower, velMultLin, accMultLin, perWheelPIDCosnts, collapseConstaPerWheel,
				angularPIDConsts, collapseConstAngular, isReverse, 0, 0);
	}

	public Follow2DProfileCommand(List<State> path,
	                              double jump,
	                              int smoothingTail,
	                              ProfilingData data,
	                              double maxPower,
	                              double velMultLin,
	                              double accMultLin,
	                              PIDObject perWheelPIDCosnts,
	                              double collapseConstaPerWheel,
	                              PIDObject angularPIDConsts,
	                              double collapseConstAngular,
	                              boolean isReverse,
	                              double startV,
	                              double endV) {
		this.startV = startV;
		this.endV = endV;
		if (isReverse) {
			for (State s : path) {
				s.setAngle(s.getAngle() + Math.PI);
			}
		}
		this.profile2D = ChassisProfiler2D.generateProfile(
				path,
				jump,
				this.startV,
				this.endV,
				data,
				0.0,
				1.0,
				smoothingTail);
		SmartDashboard.putNumber("Profile Tend", this.profile2D.getTEnd());
		SmartDashboard.putString("Data for profile", data.toString());
		this.linKv = velMultLin / data.getMaxLinearVelocity();
		this.linKa = accMultLin / data.getMaxLinearAccel();
		this.perWheelPIDConsts = perWheelPIDCosnts;
		this.collapsingPerWheelPIDTol = collapseConstaPerWheel;
		this.isOpp = isReverse;
		this.angularPIDConsts = angularPIDConsts;
		this.collapsingAngularPIDTol = collapseConstAngular;
		this.maxPower = maxPower;
		follower = new PidFollower2D(linKv, linKa, linKv, linKa,
				perWheelPIDConsts,
				collapsingPerWheelPIDTol, Double.NaN, angularPIDConsts, collapsingAngularPIDTol,
				RobotMap.Pegasus.Chassis.WHEEL_DIST,
				profile2D);
		follower.setConverter(new CurvatureConverter(RobotMap.Pegasus.Chassis.WHEEL_DIST));
	}

	private static ProfilingData retNullandThrow() {
		throw new RuntimeException("You are really really dumb, the key does not exists in robot map");
	}

	@Override
	public void atInit() {
		if (sendData != null) {
			follower.setSendData(sendData);
		} else {
			follower.setSendData(true);
		}
		Chassis.getInstance().toCoast();
		mult = isOpp ? -1 : 1;
		died = false;
		follower.init();
	}

	public void setSendData(boolean val) {
		if (follower != null) {
			follower.setSendData(val);
		} else {
			sendData = val;
		}
	}

	@Override
	public void run() {
		runTStart = System.currentTimeMillis();

		double[] inp = ProfilingUtils.trasnformInputs(Chassis.getInstance().getLeftRate(),
				Chassis.getInstance().getRightRate(),
				Chassis.getInstance().getAngularVelocityByWheels(),
				mult);
		Vector2D vals = follower.run(inp[0], inp[1], inp[2]);

		vals = ProfilingUtils.Clamp(ProfilingUtils.flipToBackwards(vals, isOpp), maxPower);

		Chassis.getInstance().moveMotors(vals.getX(), vals.getY());

		if (minRuntime != 0) {
			try {
				Thread.sleep(minRuntime);
			} catch (InterruptedException e) {
				died = true;
				e.printStackTrace();
			}
		}
	}


	/**
	 * @return if follower finished
	 */
	@Override
	public boolean isFinished() {
		return follower.isFinished();// || died;
	}

	@Override
	public void atEnd() {
		if (this.endV == 0) {
			Chassis.getInstance().toBrake();
			Chassis.getInstance().moveMotors(0, 0);
		}
	}

}
