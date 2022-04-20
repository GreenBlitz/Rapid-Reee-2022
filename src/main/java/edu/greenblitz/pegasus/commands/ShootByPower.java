package edu.greenblitz.pegasus.commands;

public class ShootByPower extends ShooterCommand {
	private double power;

	public ShootByPower(double power) {
		super();
		this.power = power;
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		shooter.SetPower(power);
		System.out.println("hello");
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		shooter.SetPower(0);
	}
}
