package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CombineJoystickMovement extends SwerveCommand{

	static final double ANG_SPEED_FACTOR = 1;

	public final SmartJoystick joystick;

	public CombineJoystickMovement(SmartJoystick joystick){
		this.joystick =joystick;

	}
	public void execute(){
		double rightwardSpeed = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
		double forwardSpeed = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		double angSpeed = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X) * ANG_SPEED_FACTOR;
		SmartDashboard.putNumber("xVal", angSpeed);
		if(forwardSpeed==0 && rightwardSpeed==0 && angSpeed==0){
			swerve.stop();
			return;
		}
		swerve.MoveByChassisSpeeds(forwardSpeed,rightwardSpeed,angSpeed,
				swerve.getChassisAngle());//put Current angle in CurrentAng (pidgen)
	}

}
