package edu.greenblitz.pegasus.commands.shooterCommands;

public class ShootByPower extends ShooterCommand {
	private double power;

	public ShootByPower(double power) {
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
