package edu.greenblitz.pegasus.commands.shooterCommands;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class ShootByValue extends ShooterCommand {

	private double power;

	public ShootByValue(double power){
		this.power = power;
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		shooter.SetPower(power);
	}

	@Override
	public void end(boolean interrupted) {
		shooter.SetPower(0);
	}

	@Override
	public boolean isFinished() {
		return super.isFinished();
	}
}
