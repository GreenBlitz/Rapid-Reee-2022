package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class ClimbCommand extends GBCommand {
	protected Climb climb;

	public ClimbCommand() {
		require(RobotContainer.getInstance().getClimb());
		climb = RobotContainer.getInstance().getClimb();
		require(climb.getTurning());
		require(climb.getRail());
	}
}
