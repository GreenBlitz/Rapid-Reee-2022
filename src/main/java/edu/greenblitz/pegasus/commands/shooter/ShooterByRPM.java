package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.RobotMap;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.pid.PIDObject;

public class ShooterByRPM extends ShooterCommand {
	protected PIDObject obj;
	protected double iZone;
	protected RemoteCSVTarget logger;
	protected double target;
	protected double tStart;
	private double epsilon = 10;
	private int inShootingSpeed;

	public ShooterByRPM(PIDObject obj, double iZone, double target) {
		this.obj = obj;
		this.obj.setKf(RobotMap.Pegasus.Shooter.ShooterMotor.RPM_TO_POWER.linearlyInterpolate(target)[0] / target);
		this.iZone = iZone;
		this.target = target;
		this.logger = RemoteCSVTarget.initTarget("FlyWheelVel", "time", "vel");
		this.inShootingSpeed = 0;
	}

	public ShooterByRPM(PIDObject obj, double target) {
		this(obj, 0, target);
	}

	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
		shooter.getPIDController().setIAccum(0.0);
		shooter.setPIDConsts(obj, iZone);
		tStart = System.currentTimeMillis()/1000.0;
	}

	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		System.out.println(shooter.getShooterSpeed());
		if (Math.abs(target - shooter.getShooterSpeed()) < epsilon) {
			this.inShootingSpeed++;

		} else {
			this.inShootingSpeed = 0;
			System.out.println("weird");
			shooter.setPreparedToShoot(false);
		}
		if (this.inShootingSpeed >= 7) {
			shooter.setPreparedToShoot(true);
		}
		logger.report((System.currentTimeMillis()/1000.0 - tStart), shooter.getShooterSpeed());
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {

		shooter.setPreparedToShoot(false);
		this.inShootingSpeed = 0;
		super.end(interrupted);
	}
}
