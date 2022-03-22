package edu.greenblitz.pegasus.commands.indexing;

public class WaitTillBallExit extends IndexingCommand {
	@Override
	public boolean isFinished() {
		return !indexing.isBallUp();
	}
}
