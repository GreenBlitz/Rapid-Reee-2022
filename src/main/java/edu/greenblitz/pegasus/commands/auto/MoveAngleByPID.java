package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveAngleByPID extends ChassisCommand {
	private PIDController pidController;
	private double angle;
	private double startingAngle;

	private static final double EPSILON = 0.1;

	public MoveAngleByPID(PIDObject object, double angle){
		this.angle = angle;
		this.pidController = new PIDController(object);
	}

	@Override
	public void initialize() {
		this.pidController.configure(0, angle,-0.5, 0.5, 0);
		this.startingAngle = chassis.getAngle();
	}

	@Override
	public void execute() {
		double angle = chassis.getAngle() - startingAngle;
		double power = pidController.calculatePID(angle);
		chassis.moveMotors(power);
	}

	@Override
	public void end(boolean interrupted) {
		chassis.moveMotors(0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(chassis.getAngle() - startingAngle - angle) < EPSILON;
	}
}
