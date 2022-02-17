package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;

public class RobotDotMove extends ChassisCommand {
	private static final double POWER = 0.1;

	@Override
	public void execute() {
		chassis.moveMotors(POWER, POWER);
	}

	@Override
	public void end(boolean interrupted) {
		chassis.moveMotors(0,0);
	}
}
