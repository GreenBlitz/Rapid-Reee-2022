package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class MoveRailSlow extends ClimbCommand{

	private SmartJoystick joystick;
	public MoveRailSlow(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		climb.safeMoveRailMotor(joystick.getAxisValue(SmartJoystick.Axis.LEFT_X));
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveRailMotor(0);
	}
}
