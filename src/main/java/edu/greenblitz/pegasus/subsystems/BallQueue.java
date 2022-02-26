package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.BallQueue.RejectWrongBalls;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class BallQueue extends GBSubsystem {
	private static BallQueue instance;
	private final I2C.Port i2cPort = I2C.Port.kOnboard;
	private final ColorSensorV3 colorSensor;
	private final DigitalInput macroSwitch;

	private static final double MARGIN_OF_ERROR = 0.2;
	private final static double THRESHOLD = 0.2;

	public enum AllianceColor {RED, BLUE, OTHER}

	private BallQueue() {
		colorSensor = new ColorSensorV3(i2cPort);
		macroSwitch = DigitalInputMap.getInstance().getDigitalInput(RobotMap.Pegasus.DigitalInputMap.MACRO_SWITCH);

		instance.setDefaultCommand(new RejectWrongBalls());
	}

	private static void init() {
		instance = new BallQueue();
	}

	public static BallQueue getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public Color getColor() {
		return colorSensor.getColor();
	}

	public boolean isBallUp(){
		return macroSwitch.get();
	}

	public AllianceColor getPerceivedColor() {
		Color color = this.getColor();
		AllianceColor perceivedColor = AllianceColor.OTHER;
		if (color.red - color.blue > (MARGIN_OF_ERROR) && color.red > THRESHOLD) {
			perceivedColor = AllianceColor.RED;
		} else if (color.blue - color.red > (MARGIN_OF_ERROR) && color.blue > THRESHOLD) {
			perceivedColor = AllianceColor.BLUE;
		}
		return perceivedColor;
	}
}
