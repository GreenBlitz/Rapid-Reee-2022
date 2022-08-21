package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class FunnelCommand extends GBCommand {

	protected Funnel funnel;

	public FunnelCommand() {
		funnel = RobotContainer.getInstance().getFunnel();
		require(funnel);
	}
}
