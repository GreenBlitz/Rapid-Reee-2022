package edu.greenblitz.icarus.commands.shooter;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.subsystems.Shooter;

public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;

	public ShooterCommand() {
		super(Shooter.getInstance());
		shooter = Shooter.getInstance();
	}
}
