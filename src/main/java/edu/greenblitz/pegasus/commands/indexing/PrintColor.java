package edu.greenblitz.pegasus.commands.indexing;

import edu.wpi.first.wpilibj.util.Color;

public class PrintColor extends IndexingCommand {
	private static double COLOR_THRESHOLD = 0.3;

	@Override
	public void execute() {
		Color color = indexing.getColor();

		double red = color.red;
		double green = color.green;
		double blue = color.blue;

		if (red > blue && red > COLOR_THRESHOLD) {
			System.out.println("Red");
		} else if (blue > red && blue > COLOR_THRESHOLD) {
			System.out.println("Blue");
		} else {
			System.out.println("Well else");
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
