package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.pegasus.RobotMap;

public class ShootByTrigger extends ShooterByRPM {

	private static final double DEADZONE = 0.1;
	private final SmartJoystick joystick;
	private final SmartJoystick.Axis axis;


	public ShootByTrigger(PIDObject obj, double target, SmartJoystick joystick, SmartJoystick.Axis axis) {
		super(obj, target, RobotMap.Pegasus.Shooter.ShooterMotor.RPM);
		this.joystick = joystick;
		this.axis = axis;
	}

	@Override
	public void execute() {
		if (joystick.getAxisValue(axis) > DEADZONE) {
			super.execute();
		}
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		shooter.setSpeedByPID(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
