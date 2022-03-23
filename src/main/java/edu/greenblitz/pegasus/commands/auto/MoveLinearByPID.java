package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveLinearByPID extends ChassisCommand {
	PIDController pidControllerLinear;
	PIDController pidControllerAngular;
	private double distance;
	double angle;
	double startingDistance;
	double maxPower;

	private static final double EPSILON = 0.02;

	public MoveLinearByPID(PIDObject linear, PIDObject angular, double distance){
		this(linear, angular, distance, 0.5);
	}

	public MoveLinearByPID(PIDObject linear, PIDObject angular, double distance, double maxPower){
		this.distance = distance;
		this.pidControllerLinear = new PIDController(linear);
		this.pidControllerAngular = new PIDController(angular);
		this.maxPower = maxPower;
	}

	@Override
	public void initialize() {
		this.angle = chassis.getAngle();
		this.pidControllerLinear.configure(chassis.getMeters(), distance,-maxPower, maxPower, 0);
		this.pidControllerAngular.configure(chassis.getAngle(), 0,-0.2, 0.2, 0);
		this.startingDistance = chassis.getMeters();
	}

	@Override
	public void execute() {
		double distance = chassis.getMeters() - startingDistance;
		double ang = chassis.getAngle() - angle;
		double power = pidControllerLinear.calculatePID(distance);
		double turn = pidControllerAngular.calculatePID(ang);
		chassis.arcadeDrive(power, turn);

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
