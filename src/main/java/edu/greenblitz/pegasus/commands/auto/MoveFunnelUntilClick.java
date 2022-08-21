package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.funnel.FunnelCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public class MoveFunnelUntilClick extends FunnelCommand {
	private final Indexing indexing;

	public MoveFunnelUntilClick() {
		this.indexing = RobotContainer.getInstance().getIndexing();
	}

	@Override
	public void execute() {
		funnel.moveMotor();
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stopMotor();
	}

	@Override
	public boolean isFinished() {
		return indexing.isBallUp();
	}
}