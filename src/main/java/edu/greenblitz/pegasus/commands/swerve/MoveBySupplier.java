package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class MoveBySupplier extends SwerveCommand {

	//we mean left, people are dumb
	static double ANG_SPEED_FACTOR = 5;//todo magic number
	static double LIN_SPEED_FACTOR = RobotMap.Pegasus.Swerve.MAX_VELOCITY;
	DoubleSupplier forward;
	DoubleSupplier right;
	DoubleSupplier ang;

	public final SmartJoystick joystick;

	private boolean isSlow;

	//supplier returns value 0-1
	public MoveBySupplier(SmartJoystick joystick, boolean isSlow, DoubleSupplier forward, DoubleSupplier right, DoubleSupplier ang) {
		this.joystick = joystick;
		this.isSlow = isSlow;
		this.forward = forward;
		this.right = right;
		this.ang = ang;
	}

	@Override
	public void initialize() {
		if (isSlow) {
			ANG_SPEED_FACTOR *= 0.5; //todo querry from robot map in initialize to prevent repeated changes
			LIN_SPEED_FACTOR *= 0.5;
		}
	}

	public void execute() {
		double rightwardSpeed = -right.getAsDouble() * LIN_SPEED_FACTOR;
		double forwardSpeed = forward.getAsDouble() * LIN_SPEED_FACTOR;
		double angSpeed = ang.getAsDouble() * ANG_SPEED_FACTOR;
		if (forwardSpeed == 0 && rightwardSpeed == 0 && angSpeed == 0) {
			swerve.stop();
			return;
		}
		swerve.MoveByChassisSpeeds(forwardSpeed, rightwardSpeed, angSpeed,
				-swerve.getChassisAngle() );
	};

}
