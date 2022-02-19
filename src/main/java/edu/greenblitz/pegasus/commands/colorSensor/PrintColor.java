package edu.greenblitz.pegasus.commands.colorSensor;

import com.revrobotics.ColorMatchResult;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.ColorSensor;
import edu.wpi.first.wpilibj.util.Color;

public class PrintColor extends GBCommand {
	ColorSensor sensor;

	private final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
	private final Color kGreenTarget = new Color(0.197, 0.561, 0.240);
	private final Color kRedTarget = new Color(0.561, 0.232, 0.114);
	private final Color kYellowTarget = new Color(0.361, 0.524, 0.113);

	public PrintColor(){
		sensor = ColorSensor.getInstance();
	}

	@Override
	public void execute() {
		String colorString;
		ColorMatchResult match = sensor.getResult();

		if (match.color == kBlueTarget) {
			colorString = "Blue";
		} else if (match.color == kRedTarget) {
			colorString = "Red";
		} else if (match.color == kGreenTarget) {
			colorString = "Green";
		} else if (match.color == kYellowTarget) {
			colorString = "Yellow";
		} else {
			colorString = "Unknown";
		}
		System.out.println(colorString);
	}
}
