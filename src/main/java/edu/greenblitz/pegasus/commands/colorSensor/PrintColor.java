package edu.greenblitz.pegasus.commands.colorSensor;

import com.revrobotics.ColorMatchResult;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.ColorSensor;
import edu.wpi.first.wpilibj.util.Color;

public class PrintColor extends GBCommand {
	ColorSensor sensor;

	public PrintColor(){
		sensor = ColorSensor.getInstance();
	}

	@Override
	public void execute() {
		Color color = sensor.getColor();

		System.out.println("Red: " + color.red);
		System.out.println("Green: " + color.green);
		System.out.println("Blue: " + color.blue);
		System.out.println("------------------------------------");
	}
}
