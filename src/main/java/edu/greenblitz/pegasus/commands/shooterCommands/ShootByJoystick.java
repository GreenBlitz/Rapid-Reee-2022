package edu.greenblitz.pegasus.commands.shooterCommands;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;

public class ShootByJoystick extends ShooterCommand{

	public ShootByJoystick(){

	}

	@Override
	public void execute() {
		shooter.setPower(OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_Y)*0.5);
	}
}
