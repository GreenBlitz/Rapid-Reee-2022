package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;

public class CombineJoystickMovement extends SwerveCommand {
	private DoubleSupplier angSupplier;
	static double ANG_SPEED_FACTOR = 5;//todo magic number
	static double LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY;

	private boolean isSlow;
	
	public CombineJoystickMovement(boolean isSlow, DoubleSupplier angSupplier){
		this.isSlow = isSlow;
		if (isSlow) {
			ANG_SPEED_FACTOR *= 0.8; //todo querry from robot map in initialize to prevent repeated changes
			LIN_SPEED_FACTOR *= 0.5;

		}
		this.angSupplier = angSupplier;
	}
	public CombineJoystickMovement(boolean isSlow){
		this(isSlow, ()->OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X));
	}

	@Override
	public void initialize() {
		if (isSlow) {
			ANG_SPEED_FACTOR *= 0.8; //todo querry from robot map in initialize to prevent repeated changes
			LIN_SPEED_FACTOR *= 0.5;
			
		}
	}
	
	public void execute() {
		double leftwardSpeed = -OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X)* LIN_SPEED_FACTOR;
		double forwardSpeed = OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y) * LIN_SPEED_FACTOR;
		double angSpeed = angSupplier.getAsDouble() * ANG_SPEED_FACTOR;
		if (forwardSpeed == 0 && leftwardSpeed == 0 && angSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.MoveByChassisSpeeds(forwardSpeed, leftwardSpeed, angSpeed,
				-swerve.getChassisAngle());
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		swerve.stop();
	}
}
