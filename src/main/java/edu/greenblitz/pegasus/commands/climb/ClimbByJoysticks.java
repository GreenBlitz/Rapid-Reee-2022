package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class ClimbByJoysticks extends ClimbCommand{

	private SmartJoystick joystick;

	public ClimbByJoysticks(SmartJoystick joystick){
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.safeMoveRailMotor(railMotorPower);

		double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
		climb.moveTurningMotor(turningMotorPower);
	}

	@Override
	public void end(boolean interrupted) {
	}
}
