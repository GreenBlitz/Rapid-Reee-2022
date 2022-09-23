package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;

public class MoveByJoystick extends SwerveCommand {
	private final double maxSpeed;
	private final SmartJoystick joystick;
	
	public MoveByJoystick(SmartJoystick joystick, double maxSpeed) {
		this.joystick = joystick;
		this.maxSpeed = maxSpeed;
	}
	
	@Override
	public void initialize() {
		swerve.configPID(RobotMap.Pegasus.Swerve.angPID, RobotMap.Pegasus.Swerve.linPID);
	}
	
	@Override
	public void execute() {
		double x = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
		double y = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		double angle = Math.atan2(y, x) * -1 + Math.PI / 2 + Math.PI / 2;//we added 1 Math.PI/2 to fix wrong lamprey calibration
		double amplitude = Math.hypot(x, y) * this.maxSpeed;
//		SmartDashboard.putNumber("pow", amplitude);
//		SmartDashboard.putNumber("x", x);
//		SmartDashboard.putNumber("y", y);
//		SmartDashboard.putNumber("badJoystickAngle", Math.toDegrees(badAngle));
//		SmartDashboard.putNumber("goodJoystickAngle", Math.toDegrees(angle));
//		SmartDashboard.putNumber("bakarAngle", swerve.getTarget(SwerveChassis.Module.BACK_RIGHT));
//		SmartDashboard.putNumber("amplitude", amplitude);
//		SmartDashboard.putNumber("curr angle",(swerve.getAngle(SwerveChassis.Module.BACK_RIGHT)));
		
		if (x == 0 && y == 0) {
			swerve.stop();
			return;
		}
		
		swerve.moveChassisLin(angle, amplitude);
	}
	
	
}

