//package edu.greenblitz.pegasus.commands.auto;
//
//
//import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.greenblitz.motion.pid.PIDController;
//import org.greenblitz.motion.pid.PIDObject;
//
//public class MoveLinearWithoutPID extends ChassisCommand {
//	private static final double EPSILON = 0.05;
//	private final double distance;
//	PIDController pidControllerAngular;
//	double angle;
//	double startingDistance;
//	double maxPower;
//	double power;
//
//	public MoveLinearWithoutPID(PIDObject angular, double distance, double power) {
//		this(angular, distance, 0.5, -10, power);
//	}
//
//	public MoveLinearWithoutPID(PIDObject angular, double distance, double angle, double power) {
//		this(angular, distance, 0.5, angle, power);
//	}
//
//	public MoveLinearWithoutPID(PIDObject angular, double distance, double maxPower, double angle, double power) {
//		this.distance = distance;
//		this.pidControllerAngular = new PIDController(angular);
//		this.maxPower = maxPower;
//		this.angle = angle;
//		this.power = power;
//	}
//
//	@Override
//	public void initialize() {
//		if (angle == -10) {
//			this.angle = chassis.getAngle();
//		}
//		this.pidControllerAngular.configure(chassis.getAngle(), 0, -0.2, 0.2, 0);
//		this.startingDistance = chassis.getMeters();
//	}
//
//	@Override
//	public void execute() {
//		double distance = chassis.getMeters() - startingDistance;
//		double ang = chassis.getAngle() - angle;
//		double power = this.power * Math.signum(this.distance);
//		double turn = pidControllerAngular.calculatePID(ang);
//		chassis.arcadeDrive(power, turn);
//
//		SmartDashboard.putNumber("Distance", chassis.getMeters() - startingDistance);
//		SmartDashboard.putNumber("Angle", chassis.getAngle());
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		chassis.moveMotors(0);
//	}
//
//	@Override
//	public boolean isFinished() {
//		return Math.abs(chassis.getMeters() - startingDistance - distance) < EPSILON;
//	}
//}
