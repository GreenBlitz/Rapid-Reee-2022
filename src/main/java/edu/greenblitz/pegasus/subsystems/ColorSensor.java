package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor extends GBSubsystem {
	private static ColorSensor instance;
	private final I2C.Port i2cPort = I2C.Port.kOnboard;
	private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);

	private ColorSensor() {

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

	public String getPerceivedColor(){
		Color color = this.getColor();
		double marginOfError = 1.2; // Above 1
		String perceivedColor = "NONE";
		if (color.red > color.blue * (marginOfError)) {
			System.out.println("RED");
			System.out.println("Red: " + color.red);
			System.out.println("blue: " + color.blue);
			System.out.println("------------------------------------");
			perceivedColor = "RED";
		} else if (color.blue > color.red * (marginOfError)) {
			System.out.println("BLUE");
			System.out.println("red: " + color.red);
			System.out.println("blue" + color.blue);
			System.out.println("------------------------------------");
			perceivedColor = "BLUE";
		} else {
			System.out.println("NONE");
			System.out.println("red: " + color.red);
			System.out.println("blue" + color.blue);
			System.out.println("------------------------------------");
			perceivedColor = "NONE";
		}
		return perceivedColor;
	}
}
