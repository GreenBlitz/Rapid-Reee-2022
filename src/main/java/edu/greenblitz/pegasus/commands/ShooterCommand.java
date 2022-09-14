package edu.greenblitz.pegasus.commands;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public abstract class ShooterCommand extends GBCommand {
	Shooter shooter;

	public ShooterCommand(){
		shooter = Shooter.getInstance();
		require(shooter);
	}


}
