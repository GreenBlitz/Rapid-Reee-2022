package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class RailCommand extends GBCommand {

	protected Climb climb;

	public RailCommand() {
		climb = RobotContainer.getInstance().getClimb();
		require(climb.getRail());
	}

}
