package edu.greenblitz.pegasus.commands.chassis;

public class LineAuto extends ChassisCommand{
	//makes chassis activate with same power for both wheels
	private double power;

	public LineAuto(double power){
		this.power = power;
	}

	@Override
	public void execute() {
		chassis.moveMotors(power, power);
	}

	@Override
	public void end(boolean interrupted) {
		chassis.moveMotors(0,0);
	}
}
