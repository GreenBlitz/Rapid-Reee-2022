package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class Indexing extends GBSubsystem {
	private final ColorSensorV3 colorSensor;
	private final DigitalInput macroSwitch;
	private int ballCount;
	private DriverStation.Alliance allianceColor;

	private static Indexing instance;

	private static final I2C.Port i2cPort = I2C.Port.kOnboard;
	private static final double MARGIN_OF_ERROR = 0.1;
	private static final double THRESHOLD = 0.3;
	private static final int MAX_BALL_COUNT = 2;

	private Indexing() {
		colorSensor = new ColorSensorV3(i2cPort);
		macroSwitch = DigitalInputMap.getInstance().getDigitalInput(RobotMap.Pegasus.Funnel.MACRO_SWITCH_PORT);
		ballCount = 0;
		System.out.println(DriverStation.getAlliance());
	}

	private static void init() {
		instance = new Indexing();
	}

	public static Indexing getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public Color getColor() throws Exception {
		Color color = colorSensor.getColor();
		if(color.blue == color.red && color.red == color.green && color.green == 0){
			throw new Exception("Color sensor is not connected");
		}
		return color;
	}

	public boolean isBallUp(){
		return macroSwitch.get();
	}

	public DriverStation.Alliance getPerceivedColor() {
		try {
			Color color = this.getColor();
			if (color.red - color.blue > MARGIN_OF_ERROR && color.red > THRESHOLD) {
				return DriverStation.Alliance.Red;
			} else if (color.blue - color.red > MARGIN_OF_ERROR && color.blue > THRESHOLD) {
				return DriverStation.Alliance.Blue;
			}
			return DriverStation.Alliance.Invalid;
		} catch (Exception e) {
			return DriverStation.Alliance.Invalid;
		}
	}

	public DriverStation.Alliance getAllianceColor(){
		return this.allianceColor;
	}

	public void addBall(){
		if(this.ballCount < MAX_BALL_COUNT) {
			this.ballCount++;
		}
	}

	public void resetBallCount(){
		this.ballCount = 0;
	}

	public int getBallCount(){
		return this.ballCount;
	}

	public void initSetAlliance(){
		allianceColor = DriverStation.getAlliance();
	}
}
