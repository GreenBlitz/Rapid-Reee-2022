package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.motion.pid.PIDObject;
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
		SmartDashboard.putNumber("angle", moduleTest.getAbsoluteAngle());
		object.setKp(SmartDashboard.getNumber("kp", object.getKp()));
		object.setKi(SmartDashboard.getNumber("ki", object.getKi()));
		object.setKd(SmartDashboard.getNumber("kd", object.getKd()));
		object.setFF(SmartDashboard.getNumber("ff", object.getKf()));
		object.setIZone(SmartDashboard.getNumber("iZone", object.getIZone()));
		object.setMaxPower(SmartDashboard.getNumber("maxPower", object.getMaxPower()));
		SmartDashboard.putNumber("curr target",moduleTest.getCurrentAngle());
		SmartDashboard.putNumber("is reversed",moduleTest.getIsReversed());
		target = SmartDashboard.getNumber("target", target);
		moduleTest.configAnglePID(object);
		moduleTest.rotateToAngle(target);
	}
}
