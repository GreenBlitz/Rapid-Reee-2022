package edu.greenblitz.pegasus.commands.chassis;

public class MoveMotorByID extends ChassisCommand {
	private double power;
	private int id;
	
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
