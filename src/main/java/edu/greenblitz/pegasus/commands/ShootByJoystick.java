package edu.greenblitz.pegasus.commands;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;

public class ShootByJoystick extends ShooterCommand{
	
	@Override
	public void execute() {
		shooter.setPower(OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_Y));
	}
}
