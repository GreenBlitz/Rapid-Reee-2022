package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.funnel.FunnelCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;

public class MoveFunnelUntilClick extends FunnelCommand {
	private final Indexing indexing;

	public MoveFunnelUntilClick() {
		this.indexing = Indexing.getInstance();
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