package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.pegasus.commands.colorSensor.RejectWrongBalls;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

public class ColorSensor extends GBSubsystem {
	private static ColorSensor instance;
	private final I2C.Port i2cPort = I2C.Port.kOnboard;
	private final ColorSensorV3 colorSensor;

	private static final double MARGIN_OF_ERROR = 0.2;
	private final static double THRESHOLD = 0.2;

	public enum AllianceColor {RED, BLUE, OTHER}

	private ColorSensor() {
		colorSensor = new ColorSensorV3(i2cPort);

		instance.setDefaultCommand(new RejectWrongBalls());
	}

	private static void init() {
		instance = new ColorSensor();
	}

	public static ColorSensor getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public Color getColor() {
		return colorSensor.getColor();
	}

	public AllianceColor getPerceivedColor() {
		Color color = this.getColor();
		AllianceColor perceivedColor = AllianceColor.OTHER;
		if (color.red - color.blue > (MARGIN_OF_ERROR) && color.red > THRESHOLD) {
			perceivedColor = AllianceColor.RED;
		} else if (color.blue - color.red > (MARGIN_OF_ERROR) && color.blue > THRESHOLD) {
			perceivedColor = AllianceColor.BLUE;
		} else {
			perceivedColor = AllianceColor.OTHER;
		}
		System.out.println(perceivedColor);
		System.out.println("Red: " + color.red);
		System.out.println("Blue: " + color.blue);
		System.out.println("------------------------------------");
		return perceivedColor;
	}
}
