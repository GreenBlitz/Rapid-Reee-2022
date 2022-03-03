package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class MoveTurningSlow extends ClimbCommand{

	private SmartJoystick joystick;
	public MoveTurningSlow(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		climb.moveTurningMotor(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y));
	}

	public void end(boolean interrupted) {
		climb.moveTurningMotor(0);
	}

}
