package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

public class SwitchTurning extends TurningCommand {
	private final SmartJoystick joystick;
	private final SmartJoystick nextJoystick;

	public SwitchTurning(SmartJoystick joystick, SmartJoystick nextJoystick) {
		this.joystick = joystick;
		this.nextJoystick = nextJoystick;
	}

	@Override
	public void execute() {
		double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER) - joystick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER);

		climb.safeMoveTurningMotor(turningMotorPower * 0.3);
	}

	@Override
	public void end(boolean interrupted) {
		new ScheduleCommand(new TurningByJoystick(nextJoystick));
	}
}
