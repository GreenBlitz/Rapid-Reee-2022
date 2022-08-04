package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.gblib.subsystems.Chassis.ChassisCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveLinearByPID extends ChassisCommand {
	private static final double EPSILON = 0.05;
	private final double distance;
	PIDController pidControllerLinear;
	PIDController pidControllerAngular;
	double angle;
	double startingDistance;
	double maxPower;

	public MoveLinearByPID(PIDObject linear, PIDObject angular, double distance) {
		this(linear, angular, distance, 0.5, -10);
	}

	public MoveLinearByPID(PIDObject linear, PIDObject angular, double distance, double angle) {
		this(linear, angular, distance, 0.5, angle);
	}

	public MoveLinearByPID(PIDObject linear, PIDObject angular, double distance, double maxPower, double angle) {
		this.distance = distance;
		this.pidControllerLinear = new PIDController(linear);
		this.pidControllerAngular = new PIDController(angular);
		this.maxPower = maxPower;
		this.angle = angle;
	}

	@Override
	public void initialize() {
		if (angle == -10) {
			this.angle = chassis.getAngle();
		}
		this.pidControllerLinear.configure(chassis.getMeters(), distance, -maxPower, maxPower, 0);
		this.pidControllerAngular.configure(chassis.getAngle(), 0, -0.4, 0.4, 0);
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
		SmartDashboard.putNumber("Angle", chassis.getAngle());
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
