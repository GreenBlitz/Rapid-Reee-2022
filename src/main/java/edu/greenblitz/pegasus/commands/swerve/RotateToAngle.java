package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.utils.GBMath;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotateToAngle extends SwerveCommand{
	private double target;
	private PIDObject object = new PIDObject(0);
	public RotateToAngle(double target){
		this.target = target;
	}
	@Override
	public void initialize() {
		SmartDashboard.putNumber("kp", object.getKp());
		SmartDashboard.putNumber("ki", object.getKi());
		SmartDashboard.putNumber("kd", object.getKd());
		SmartDashboard.putNumber("ff", object.getKf());
		SmartDashboard.putNumber("iZone", object.getIZone());
		SmartDashboard.putNumber("target", target);
		SmartDashboard.putNumber("maxPower", object.getMaxPower());
		moduleTest.configAnglePID(object);
		moduleTest.rotateToAngle(target);
		
	}
	
	@Override
	public void execute() {
		SmartDashboard.putNumber("angle", moduleTest.getLampreyAngle());
		object.setKp(SmartDashboard.getNumber("kp", object.getKp()));
		object.setKi(SmartDashboard.getNumber("ki", object.getKi()));
		object.setKd(SmartDashboard.getNumber("kd", object.getKd()));
		object.setFF(SmartDashboard.getNumber("ff", object.getKf()));
		object.setIZone(SmartDashboard.getNumber("iZone", object.getIZone()));
		object.setMaxPower(SmartDashboard.getNumber("maxPower", object.getMaxPower()));
		SmartDashboard.putNumber("curr target",moduleTest.getMotorAngle());
		SmartDashboard.putNumber("is reversed",moduleTest.getIsReversed());
		SmartDashboard.putNumber("raw angle", GBMath.modulo(normalizeAngleToDegrees(moduleTest.getRawLampreyAngle() / 360 * 4076), 360));
		target = SmartDashboard.getNumber("target", target);
		moduleTest.configAnglePID(object);
		moduleTest.rotateToAngle(target);
	}

	private final static double lampreyMinAng = 95;
	private final static double lampreyMaxAng = 4034;

	private double normalizeAngleToDegrees(double angle){
		return (angle - lampreyMinAng) / (lampreyMaxAng - lampreyMinAng) * 360 ;
	}
}
