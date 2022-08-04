package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;

public class Funnel extends GBSubsystem {
	private static Funnel instance;
	private final WPI_TalonSRX motor;

	private Funnel() {
		motor = new WPI_TalonSRX(RobotMap.Pegasus.Funnel.FunnelMotor.MOTOR_PORT);
		motor.setInverted(RobotMap.Pegasus.Funnel.FunnelMotor.IS_REVERSED);
	}

	private static void init() {
		instance = new Funnel();
	}

	public static Funnel getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
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
