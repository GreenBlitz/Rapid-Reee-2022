package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.motion.pid.PIDObject;

public class ShootByTrigger extends ShooterByRPM {

	private static final double DEADZONE = 0.1;
	private final SmartJoystick joystick;
	private final SmartJoystick.Axis axis;


	public ShootByTrigger(PIDObject obj, double target, SmartJoystick joystick, SmartJoystick.Axis axis) {
		super(obj, target);
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
