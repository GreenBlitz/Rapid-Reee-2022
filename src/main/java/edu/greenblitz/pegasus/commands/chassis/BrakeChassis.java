package edu.greenblitz.pegasus.commands.chassis;


import edu.greenblitz.gblib.subsystems.Chassis.ChassisCommand;


public class BrakeChassis extends ChassisCommand {
	@Override
	public void initialize() {
		chassis.moveMotors(0, 0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
