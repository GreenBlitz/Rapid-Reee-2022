package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor extends GBSubsystem{
	private static ColorSensor instance;
	private final I2C.Port i2cPort = I2C.Port.kOnboard;
	private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

	private ColorSensor(){

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

	public Color getColor(){
		return m_colorSensor.getColor();
	}
}
