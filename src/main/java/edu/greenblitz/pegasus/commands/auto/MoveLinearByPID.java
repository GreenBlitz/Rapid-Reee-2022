package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveLinearByPID extends ChassisCommand {
	PIDController pidControllerLinear;
	private double distance;
	double startingDistance;

	private static final double EPSILON = 0.02;

	public MoveLinearByPID(PIDObject linear, double distance){
		this.distance = distance;
		this.pidControllerLinear = new PIDController(linear);
	}

	@Override
	public void initialize() {
		this.pidControllerLinear.configure(chassis.getMeters(), distance,-0.5, 0.5, 0);
		this.startingDistance = chassis.getMeters();
	}

	@Override
	public void execute() {
		double distance = chassis.getMeters() - startingDistance;
		double power = pidControllerLinear.calculatePID(distance);
		chassis.arcadeDrive(power, 0);

		SmartDashboard.putNumber("Distance", chassis.getMeters() - startingDistance);
		SmartDashboard.putNumber("Angle", Chassis.getInstance().getAngle());
	}

	@Override
	public void end(boolean interrupted) {
		chassis.moveMotors(0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(chassis.getMeters() - startingDistance - distance) < EPSILON;
	}
}
