package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AngPIDSupplier implements DoubleSupplier{
	private DoubleSupplier ang;
	private PIDController chassisPid;

	public AngPIDSupplier(DoubleSupplier ang){
		SmartDashboard.putNumber("kp", 1);
		SmartDashboard.putNumber("ki", 0);
		SmartDashboard.putNumber("kd", 0);
		this.ang = ang;
		chassisPid = new PIDController(RobotMap.Pegasus.Swerve.rotationPID.getKp(),RobotMap.Pegasus.Swerve.rotationPID.getKi(),RobotMap.Pegasus.Swerve.rotationPID.getKd());
		chassisPid.enableContinuousInput(0,2*Math.PI); //min and max
	}
	
	/**
	 * vx - the speed of the robot in x.
	 * vy - the speed of the robot in y.
	 * dx - the distance from the vision target.
	 * dy - the distance in x from the vision target.
	 * @return the equitation of the feedforward(more math in this site: https://tinyurl.com/visionGB).
	 */
	private double getAngVelDiffByVision(){
		double vy = SwerveChassis.getInstance().getCurSpeed().vyMetersPerSecond;
		double vx = SwerveChassis.getInstance().getCurSpeed().vxMetersPerSecond;
		double dx = Limelight.getInstance().targetPos().getX();
		double dy = Limelight.getInstance().targetPos().getY();
		if (dx == 0 || dy == 0){return 0;}
		return ((vy*dx + vx*dy)/(dx*dx + dy*dy));
	}

	@Override
	public double getAsDouble() {
		double kp = SmartDashboard.getNumber("kp",1);
		double kd = SmartDashboard.getNumber("kd",0);
		double ki = SmartDashboard.getNumber("ki",0);
		SmartDashboard.putNumber("1get curr x",SwerveChassis.getInstance().getCurSpeed().vxMetersPerSecond);
		SmartDashboard.putNumber("1get curr y",SwerveChassis.getInstance().getCurSpeed().vyMetersPerSecond);
		SmartDashboard.putNumber("dx1", Limelight.getInstance().targetPos().getX());
		SmartDashboard.putNumber("dy1", Limelight.getInstance().targetPos().getY());
		double ff = getAngVelDiffByVision();
		chassisPid.setPID(kp,ki,kd);
		SmartDashboard.putNumber("ff", ff);
		double input = chassisPid.calculate(SwerveChassis.getInstance().getChassisAngle(),ang.getAsDouble()) + ff;
		SmartDashboard.putNumber("ang input", input);
		return input;
	}
}
