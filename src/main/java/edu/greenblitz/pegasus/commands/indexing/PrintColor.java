package edu.greenblitz.pegasus.commands.indexing;

public class PrintColor extends IndexingCommand {
	@Override
	public void execute() {
		System.out.println(indexing.getPerceivedColor());
	}
}
