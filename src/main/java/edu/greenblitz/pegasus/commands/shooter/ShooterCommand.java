package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;

	public ShooterCommand() {
		super(Shooter.getInstance());
		shooter = Shooter.getInstance();
	}
}
