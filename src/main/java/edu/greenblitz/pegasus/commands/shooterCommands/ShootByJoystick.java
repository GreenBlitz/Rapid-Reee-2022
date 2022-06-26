package edu.greenblitz.pegasus.commands.shooterCommands;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;

public class ShootByJoystick extends ShooterCommand {
	SmartJoystick mainJoystick;

	public ShootByJoystick() {
		this.mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
	}

	@Override
	public void execute() {
		shooter.setPower(mainJoystick.getAxisValue(SmartJoystick.Axis.LEFT_Y));
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPower(0);
	}
}
