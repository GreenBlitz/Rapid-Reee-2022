package edu.greenblitz.icarus.commands.complexClimb;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class ClimbByTriggers extends ComplexClimbCommand {
	private SmartJoystick motorStick;
	private double motorMultiplier;

	public ClimbByTriggers(SmartJoystick motorStick, double motorMult) {
		this.motorStick = motorStick;
		this.motorMultiplier = motorMult;
	}
	@Override
	public void execute() {
		complexClimb.moveCurrentMotor(motorMultiplier * (motorStick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER) - motorStick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER)));
	}
}
