package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;

public class TurningCommand extends GBCommand {
	
	Climb climb;
	
	public TurningCommand(){
		climb = Climb.getInstance();
		require(climb.getTurning());
	}
}
