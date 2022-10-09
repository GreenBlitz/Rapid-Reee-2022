package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CombineJoystickMovement extends SwerveCommand {

	static double ANG_SPEED_FACTOR = 3;
	static double LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY;

	private final SmartJoystick joystick;
	private boolean isSlow;

	public CombineJoystickMovement(SmartJoystick joystick, boolean isSlow) {
		this.joystick = joystick;
		this.isSlow = isSlow;
	}

	@Override
	public void initialize() {
		if (isSlow){
			ANG_SPEED_FACTOR *= 0.5;
			LIN_SPEED_FACTOR *= 0.5;
		}

	}

	public void execute() {
		double rightwardSpeed = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_X) * LIN_SPEED_FACTOR;
		double forwardSpeed = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * LIN_SPEED_FACTOR;
		double angSpeed = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X) * ANG_SPEED_FACTOR;
		if (forwardSpeed == 0 && rightwardSpeed == 0 && angSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.MoveByChassisSpeeds(forwardSpeed, rightwardSpeed, angSpeed,
				-swerve.getChassisAngle());//put Current angle in CurrentAng (pidgen)
	}

}
