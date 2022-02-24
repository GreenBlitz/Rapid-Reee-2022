package edu.greenblitz.pegasus.commands.colorSensor;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.ColorSensor;
import edu.wpi.first.wpilibj.util.Color;

public class PrintColor extends GBCommand {
	private ColorSensor colorSensor;

	private static double COLOR_THRESHOLD = 0.3;

	public PrintColor() {
		colorSensor = ColorSensor.getInstance();
	}

	@Override
	public void execute() {
		Color color = colorSensor.getColor();

		double red = color.red;
		double green = color.green;
		double blue = color.blue;

		if(red > blue && red > COLOR_THRESHOLD){
			System.out.println("Red");
		}else if(blue > red && blue > COLOR_THRESHOLD){
			System.out.println("Blue");
		}else{
			System.out.println("Well else");
		}
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
