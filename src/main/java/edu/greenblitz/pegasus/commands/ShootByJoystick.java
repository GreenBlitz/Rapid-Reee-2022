package edu.greenblitz.pegasus.commands;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class ShootByJoystick extends ShooterCommand{

	SmartJoystick joystick;
	public ShootByJoystick(SmartJoystick joystick){
		this.joystick = joystick;
	}
	@Override
	public void execute() {
		shooter.setPower(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y));
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPower(0);
	}
}
