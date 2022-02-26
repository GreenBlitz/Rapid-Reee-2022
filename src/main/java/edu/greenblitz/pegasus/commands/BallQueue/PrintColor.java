package edu.greenblitz.pegasus.commands.BallQueue;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.BallQueue;
import edu.wpi.first.wpilibj.util.Color;

public class PrintColor extends GBCommand {
	BallQueue colorSensor;

	public PrintColor() {
		colorSensor = BallQueue.getInstance();
	}

	@Override
	public void execute() {
		Color color = colorSensor.getColor();

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
