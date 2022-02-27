package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.ColorSensorV3;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.indexing.RejectWrongBalls;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.Queue;

public class Indexing extends GBSubsystem {
	public Queue<BallColor> ballQueue; // only works if roll by ball queue is only roller command scheduled
	private final I2C.Port i2cPort = I2C.Port.kOnboard;
	private final ColorSensorV3 colorSensor;
	private final DigitalInput macroSwitch;
	private int ballCount;
	private BallColor allianceColor;

	private static Indexing instance;

	private static final double MARGIN_OF_ERROR = 0.2;
	private static final double THRESHOLD = 0.2;

	public enum BallColor {RED, BLUE, OTHER}

	private Indexing() {
		colorSensor = new ColorSensorV3(i2cPort);
		macroSwitch = DigitalInputMap.getInstance().getDigitalInput(RobotMap.Pegasus.DigitalInputMap.MACRO_SWITCH);
		ballCount = 0;
		System.out.println(DriverStation.getAlliance());
		allianceColor = DriverStation.getAlliance() == DriverStation.Alliance.Blue ? BallColor.BLUE : BallColor.RED;
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

	public Color getColor() {
		return colorSensor.getColor();
	}

	public boolean isBallUp(){
		return macroSwitch.get();
	}

	public BallColor getPerceivedColor() {
		Color color = this.getColor();
		BallColor perceivedColor = BallColor.OTHER;
		if (color.red - color.blue > (MARGIN_OF_ERROR) && color.red > THRESHOLD) {
			perceivedColor = BallColor.RED;
		} else if (color.blue - color.red > (MARGIN_OF_ERROR) && color.blue > THRESHOLD) {
			perceivedColor = BallColor.BLUE;
		}
		return perceivedColor;
	}

	public BallColor getAllianceColor(){
		return this.allianceColor;
	}

	public void addBall(){
		if(this.ballCount < 2)
			this.ballCount++;
	}

	public void removeBall(){
		if(this.ballCount > 0)
			this.ballCount--;
	}

	public int getBallCount(){
		return this.ballCount;
	}
}
