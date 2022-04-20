package edu.greenblitz.pegasus.commands;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.RobotMap;

public class ShootByTrigger extends ShooterCommand {
	private double power= 0.1;
	private SmartJoystick mainJoystick;

	public ShootByTrigger() {
		super();
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		this.power = mainJoystick.getRawJoystick().getY()*0.15;
		shooter.SetPower(this.power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		shooter.SetPower(0);
	}
}
