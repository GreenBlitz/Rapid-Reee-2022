package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.pegasus.subsystems.Indexing;

public class PrintColor extends IndexingCommand{
	@Override
	public void execute() {
		System.out.println(indexing.isBallUp());
	}
}
