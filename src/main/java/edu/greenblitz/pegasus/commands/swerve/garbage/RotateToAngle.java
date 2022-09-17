//
//package edu.greenblitz.pegasus.commands.swerve;
//
//import edu.greenblitz.gblib.motion.pid.PIDObject;
//import edu.greenblitz.gblib.utils.GBMath;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//
//public class RotateToAngle extends SwerveCommand {
//	private final static double lampreyMinAng = 95;
//	private final static double lampreyMaxAng = 4034;
//	private double target;
//	private final PIDObject object = new PIDObject().withKp(0.1).withMaxPower(0.05);
//
//	public RotateToAngle(double target) {
//		this.target = target;
//	}
//
//	@Override
//	public void initialize() {
//		SmartDashboard.putNumber("kp", object.getKp());
//		SmartDashboard.putNumber("ki", object.getKi());
//		SmartDashboard.putNumber("kd", object.getKd());
//		SmartDashboard.putNumber("ff", object.getKf());
//		SmartDashboard.putNumber("iZone", object.getIZone());
//		SmartDashboard.putNumber("target", target);
//		SmartDashboard.putNumber("maxPower", object.getMaxPower());
//		ModuleTest.getInstance().configAnglePID(object);
//		ModuleTest.getInstance().rotateToAngle(target);
//
//	}
//
//	@Override
//	public void execute() {
//		SmartDashboard.putNumber("angle", ModuleTest.getInstance().getLampreyAngle());
//		object.setKp(SmartDashboard.getNumber("kp", object.getKp()));
//		object.setKi(SmartDashboard.getNumber("ki", object.getKi()));
//		object.setKd(SmartDashboard.getNumber("kd", object.getKd()));
//		object.setFF(SmartDashboard.getNumber("ff", object.getKf()));
//		object.setIZone(SmartDashboard.getNumber("iZone", object.getIZone()));
//		object.setMaxPower(SmartDashboard.getNumber("maxPower", object.getMaxPower()));
//		SmartDashboard.putNumber("motor angle", ModuleTest.getInstance().getMotorAngle());
////		SmartDashboard.putNumber("curr target",ModuleTest.getInstance().getMotorAngle());
//		SmartDashboard.putNumber("is reversed", ModuleTest.getInstance().getIsReversed());
//		SmartDashboard.putNumber("raw angle", GBMath.modulo(normalizeAngleToDegrees(ModuleTest.getInstance().getRawLampreyAngle() / 360 * 4076), 360));
//		target = SmartDashboard.getNumber("target", target);
//		ModuleTest.getInstance().configAnglePID(object);
//		ModuleTest.getInstance().rotateToAngle(target);
//	}
//
//	private double normalizeAngleToDegrees(double angle) {
//		return (angle - lampreyMinAng) / (lampreyMaxAng - lampreyMinAng) * 360;
//	}
//}
