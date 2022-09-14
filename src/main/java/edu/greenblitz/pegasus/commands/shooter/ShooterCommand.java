package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;


public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;

	public ShooterCommand() {
		super(Shooter.getInstance());
		shooter = Shooter.getInstance();
	}
}
