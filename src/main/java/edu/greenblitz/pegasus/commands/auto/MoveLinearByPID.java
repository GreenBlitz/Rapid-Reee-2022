package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveLinearByPID extends ChassisCommand {
	private PIDController pidControllerLinear;
	private PIDController pidControllerAngular;
	private double distance;
	private double startingDistance, startingAngle;

	private static final double EPSILON = 0.05;

	public MoveLinearByPID(PIDObject linear, PIDObject angular, double distance){
		this.distance = distance;
		this.pidControllerLinear = new PIDController(linear);
		this.pidControllerAngular = new PIDController(angular);
	}

	@Override
	public void initialize() {
		this.pidControllerLinear.configure(0,distance,-0.5, 0.5, 0);
		this.pidControllerAngular.configure(0, 0, -0.5, 0.5, 0);
		this.startingDistance = chassis.getMeters();
		this.startingAngle = chassis.getAngle();
	}

	@Override
	public void execute() {
		double distance = chassis.getMeters() - startingDistance;
		double turn = chassis.getAngle() - startingAngle;
		double power = pidControllerLinear.calculatePID(distance);
		double powerTurn = pidControllerAngular.calculatePID(turn);
		chassis.moveMotors(power + powerTurn, power - powerTurn);

		SmartDashboard.putNumber("Distance", Chassis.getInstance().getMeters());
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
