package edu.greenblitz.pegasus.commands.indexing;

public class PrintColor extends IndexingCommand{
	@Override
	public void execute() {
		try {
			System.out.println(indexing.getColor());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
