package edu.greenblitz.pegasus.commands.BallQueue;

import edu.greenblitz.pegasus.subsystems.BallQueue;

public class WhileFirstBall extends BallQueueCommand {
	@Override
	public boolean isFinished() {
		return ballQueue.isBallUp();
	}
}
