package edu.greenblitz.pegasus.commands.chassis;

import edu.greenblitz.gblib.subsystems.Chassis.ChassisCommand;

public class MoveMotorByID extends ChassisCommand {
	private final double power;
	private final int id;

	public MoveMotorByID(int id, double power) {
		this.power = power;
		this.id = id;
	}

	@Override
	public void execute() {
		chassis.setMotorByID(id, power);
	}

	@Override
	public void end(boolean interrupted) {
		chassis.setMotorByID(id, 0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
