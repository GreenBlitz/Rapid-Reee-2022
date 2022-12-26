package edu.greenblitz.pegasus.commands.swerve.measurement;

import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;

public class MoveLinearByJoystick extends SwerveCommand {
	
	public final SmartJoystick joystick;
	public MoveLinearByJoystick(SmartJoystick joystick) {
		this.joystick = joystick;
	}
	
	
	public void execute() {
		double leftwardSpeed = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
		double forwardSpeed = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		if (forwardSpeed == 0 && leftwardSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.moveChassisLin(Math.atan2(leftwardSpeed, forwardSpeed), Math.hypot(forwardSpeed, leftwardSpeed));
	}
	
}
