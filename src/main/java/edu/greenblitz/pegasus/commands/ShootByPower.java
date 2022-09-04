package edu.greenblitz.pegasus.commands;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class ShootByPower extends GBCommand {
	private double power;
	public ShootByPower(double power){
		this.power = power;
		require(Shooter.getInstance());
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		super.execute();
		Shooter.getInstance().shoot(power);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		Shooter.getInstance().shoot(0);
	}

	@Override
	public boolean isFinished() {
		return super.isFinished();
	}
}
