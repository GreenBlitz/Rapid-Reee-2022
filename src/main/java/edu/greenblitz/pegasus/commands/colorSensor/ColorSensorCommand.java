package edu.greenblitz.pegasus.commands.colorSensor;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.ColorSensor;

abstract public class ColorSensorCommand extends GBCommand {
	ColorSensor colorSensor;

	public ColorSensorCommand() {
		colorSensor = ColorSensor.getInstance();
	}
}
