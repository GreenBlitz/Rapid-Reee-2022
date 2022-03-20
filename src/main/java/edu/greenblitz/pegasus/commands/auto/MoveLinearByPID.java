package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveLinearByPID extends ChassisCommand {
	private PIDController pidController;
	private double distance;
	private double startingDistance;

	private static final double EPSILON = 0.1;

	public MoveLinearByPID(PIDObject object, double distance){
		this.distance = distance;
		this.pidController = new PIDController(object);
	}

	@Override
	public void initialize() {
		this.pidController.configure(0,distance,-0.5, 0.5, 0);
		this.startingDistance = chassis.getMeters();
	}

	@Override
	public void execute() {
		double distance = chassis.getMeters() - startingDistance;
		double power = pidController.calculatePID(distance);
		chassis.moveMotors(power);
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
