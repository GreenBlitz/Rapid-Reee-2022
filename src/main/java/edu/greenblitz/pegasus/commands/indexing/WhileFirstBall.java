package edu.greenblitz.pegasus.commands.indexing;

public class WhileFirstBall extends IndexingCommand {
	@Override
	public boolean isFinished() {
		return indexing.isBallUp();
	}
}
