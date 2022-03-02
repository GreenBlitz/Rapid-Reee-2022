package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.wpi.first.wpilibj.util.Color;

public class PrintColor extends IndexingCommand {
	@Override
	public void execute() {
		Color color = indexing.getColor();

		System.out.println("Red: " + color.red);
		System.out.println("Green: " + color.green);
		System.out.println("Blue: " + color.blue);
		System.out.println("------------------------------------");
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
