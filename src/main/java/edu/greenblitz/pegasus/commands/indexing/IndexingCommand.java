package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;

abstract public class IndexingCommand extends GBCommand {
	Indexing indexing;

	public IndexingCommand() {
		indexing = Indexing.getInstance();
		require(indexing);
	}
}
