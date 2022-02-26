package edu.greenblitz.pegasus.commands.BallQueue;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.BallQueue;

abstract public class BallQueueCommand extends GBCommand {
	BallQueue ballQueue;

	public BallQueueCommand() {
		ballQueue = BallQueue.getInstance();
	}
}
