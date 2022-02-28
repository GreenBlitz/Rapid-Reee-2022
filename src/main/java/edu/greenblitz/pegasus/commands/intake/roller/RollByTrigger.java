package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RollByTrigger extends RollerCommand {
	private SmartJoystick joystick;

	public RollByTrigger(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("RollByTrigger", true);
		double power = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER);
		intake.moveRoller(power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		SmartDashboard.putBoolean("RollByConstant", false);
		intake.moveRoller(0);
	}
}
