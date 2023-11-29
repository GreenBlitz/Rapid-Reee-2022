package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;

public class Funnel extends GBSubsystem {
	private final WPI_TalonSRX motor;

	private static Funnel instance;

	public static Funnel getInstance() {
		if (instance == null) {
			instance = new Funnel();
		}
		return instance;
	}

	protected Funnel() {
		motor = new WPI_TalonSRX(RobotMap.Pegasus.Funnel.FunnelMotor.MOTOR_PORT);
		motor.setInverted(RobotMap.Pegasus.Funnel.FunnelMotor.IS_REVERSED);
	}

	public void moveMotor(double power) {
		motor.set(power);
	}

	public void moveMotor(boolean reversed) {
		moveMotor(reversed ? RobotMap.Pegasus.Funnel.REVERSE_POWER : RobotMap.Pegasus.Funnel.POWER);
	}
	

	public void moveMotor() {
		moveMotor(false);
	}

	public void stopMotor() {
		motor.set(0);
	}
}
