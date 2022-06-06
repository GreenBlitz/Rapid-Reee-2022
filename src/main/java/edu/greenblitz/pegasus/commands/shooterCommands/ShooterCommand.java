package edu.greenblitz.pegasus.commands.shooterCommands;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Subsystem;

public abstract class ShooterCommand extends GBCommand {

	Shooter shooter;

	public ShooterCommand(){
		shooter = Shooter.getInstance();
		require(shooter);
	}

}
