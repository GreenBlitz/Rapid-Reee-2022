package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;

public abstract class ClimbCommand extends GBCommand {
	protected Climb climb;

	public ClimbCommand() {
		require(Climb.getInstance());
		climb = Climb.getInstance();
	}

}
