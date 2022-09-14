package edu.greenblitz.pegasus.commands;

public class ShootByPower extends ShooterCommand{
	double power;
	public ShootByPower(double power){
		this.power = power;
	}

	@Override
	public void execute() {
		shooter.setPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPower(0);
	}
}
