package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

abstract public class IndexingCommand extends GBCommand {
	Indexing indexing;

	public IndexingCommand() {
		indexing = RobotContainer.getInstance().getIndexing();
		require(indexing);
	}
}
