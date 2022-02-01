package edu.greenblitz.icarus.commands.climb;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.subsystems.Climb;

public abstract class ClimbCommand extends GBCommand {
	
	protected Climb climb;

	public ClimbCommand() {
		climb = Climb.getInstance();
		require(climb);
	}

}





