package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class ToggleShooterByRPM extends GBCommand {
	
	public ToggleShooterByRPM(){
		super();
	}
	
	public void initialize() {
		Shooter.getInstance().toggleShooter();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
