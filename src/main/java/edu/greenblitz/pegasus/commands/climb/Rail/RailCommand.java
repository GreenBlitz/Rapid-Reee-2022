package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;

public abstract class RailCommand extends GBCommand {
	
	protected Climb climb;
	
	public RailCommand(){
		climb = Climb.getInstance();
		require(climb.getRail());
	}
	
}
