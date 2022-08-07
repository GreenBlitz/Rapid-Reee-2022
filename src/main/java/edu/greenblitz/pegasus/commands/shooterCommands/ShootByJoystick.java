package edu.greenblitz.pegasus.commands.shooterCommands;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class ShootByJoystick extends ShooterCommand{


	@Override
	public void execute() {
		shooter.setPower(OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y)*0.5);
	}
}
