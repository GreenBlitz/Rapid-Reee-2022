package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;

public class CombineJoystickMovement extends SwerveCommand {
	
	static double ANG_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_ANGULAR_SPEED;
	static double LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY;

	public final SmartJoystick joystick;
	
	private boolean isSlow;

	public CombineJoystickMovement(SmartJoystick joystick, boolean isSlow) {
		this.joystick = joystick;
		this.isSlow = isSlow;
		if (isSlow) {
			ANG_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_ANGULAR_SPEED * 0.01;
			LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY * 0.025;
		}
	}

	
	public void execute() {
		double leftwardSpeed = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_X) * LIN_SPEED_FACTOR;
		double forwardSpeed = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * LIN_SPEED_FACTOR;
		double angSpeed = -joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X) * ANG_SPEED_FACTOR;
		if (forwardSpeed == 0 && leftwardSpeed == 0 && angSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.moveByChassisSpeeds(forwardSpeed, leftwardSpeed, angSpeed,
				swerve.getChassisAngle());
	}
	
}
