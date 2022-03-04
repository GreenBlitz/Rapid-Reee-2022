package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;

abstract public class IndexingCommand extends GBCommand {
	Indexing indexing;

	public IndexingCommand() {
		indexing = Indexing.getInstance();
		require(indexing);
	}
}
