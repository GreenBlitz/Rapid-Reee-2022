package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;

public class WaitTillBallExit extends GBCommand {
	
	private int ballCount;
	
	
	@Override
	public void initialize() {
		super.initialize();
		ballCount = Indexing.getInstance().getBallCount();
	}
	
	@Override
	public boolean isFinished() {
		return ballCount>Indexing.getInstance().getBallCount();
	}
}
