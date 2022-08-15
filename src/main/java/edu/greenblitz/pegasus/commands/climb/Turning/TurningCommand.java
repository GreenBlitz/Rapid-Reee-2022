package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class TurningCommand extends GBCommand {

	Climb climb;

	public TurningCommand() {
		climb = RobotContainer.getInstance().getClimb();
		require(climb.getTurning());
	}

}
