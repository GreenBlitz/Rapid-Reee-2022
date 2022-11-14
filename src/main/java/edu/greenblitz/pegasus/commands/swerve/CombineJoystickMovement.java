package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;

public class CombineJoystickMovement extends SwerveCommand {
	
	static double ANG_SPEED_FACTOR = 5;//todo magic number
	static double LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY;

	public final SmartJoystick joystick;
	
	private boolean isSlow;

	public CombineJoystickMovement(SmartJoystick joystick, boolean isSlow) {
		this.joystick = joystick;
		this.isSlow = isSlow;
		if (isSlow) {
			ANG_SPEED_FACTOR *= 0.5; //todo querry from robot map in initialize to prevent repeated changes
			LIN_SPEED_FACTOR *= 0.3;
		}
	}
	
	public void execute() {
		double leftwardSpeed = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_X) * LIN_SPEED_FACTOR;
		double forwardSpeed = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * LIN_SPEED_FACTOR;
		double angSpeed = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X) * ANG_SPEED_FACTOR;
		if (forwardSpeed == 0 && leftwardSpeed == 0 && angSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.moveByChassisSpeeds(forwardSpeed, leftwardSpeed, angSpeed,
				swerve.getChassisAngle() );
	}
	
}
