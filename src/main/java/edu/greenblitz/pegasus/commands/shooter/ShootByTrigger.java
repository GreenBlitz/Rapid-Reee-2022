package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootByTrigger extends ShooterCommand {
	private SmartJoystick joystick;

	public ShootByTrigger(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("RollByTrigger", true);
		double power = joystick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER);
		shooter.shoot(power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		SmartDashboard.putBoolean("RollByConstant", false);
		shooter.shoot(0);
	}
}
