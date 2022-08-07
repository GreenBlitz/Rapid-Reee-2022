package edu.greenblitz.pegasus.commands.shooterCommands;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public abstract class ShooterCommand extends GBCommand {
	Shooter shooter;
	public ShooterCommand(){
		this.shooter = Shooter.getInstance();
		require(shooter);
	}
}
