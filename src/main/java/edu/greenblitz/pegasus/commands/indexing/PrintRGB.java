package edu.greenblitz.pegasus.commands.indexing;

import edu.wpi.first.wpilibj.util.Color;

public class PrintRGB extends IndexingCommand {
	@Override
	public void execute() {
		Color currColor = indexing.getColor();
		System.out.println("R: " + currColor.red);
		System.out.println("G: " + currColor.green);
		System.out.println("B: " + currColor.blue);
	}
}
