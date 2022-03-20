package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveLinearByPID extends ChassisCommand {
	private PIDController pidControllerLeft;
	private PIDController pidControllerRight;
	private double distance;
	private double startingDistance;

	private static final double EPSILON = 0.05;

	public MoveLinearByPID(PIDObject left, PIDObject right, double distance){
		this.distance = distance;
		this.pidControllerLeft = new PIDController(left);
		this.pidControllerRight = new PIDController(right);
	}

	@Override
	public void initialize() {
		this.pidControllerLeft.configure(0,distance,-0.5, 0.5, 0);
		this.pidControllerRight.configure(0, distance, -0.5, 0.5, 0);
		this.startingDistance = chassis.getMeters();
	}

	@Override
	public void execute() {
		double distance = chassis.getMeters() - startingDistance;
		double powerLeft = pidControllerLeft.calculatePID(distance);
		double powerRight = pidControllerRight.calculatePID(distance);
		chassis.moveMotors(powerLeft, powerRight);

		SmartDashboard.putNumber("DistanceLeft", Chassis.getInstance().getLeftMeters());
		SmartDashboard.putNumber("DistanceRight", Chassis.getInstance().getRightMeters());
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
