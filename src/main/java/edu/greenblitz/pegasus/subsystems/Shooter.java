package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.GBSparkMax;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByJoystick;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByPower;

import java.sql.SQLOutput;

public class Shooter extends GBSubsystem {
	private GBMotor motor;
	private static Shooter instance;
	private boolean isPowered = false;

	private Shooter() {
		motor = new GBSparkMax(7);
	}

	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}
		return instance;
	}

	public void setPower(double power) {
		motor.setPower(power);
	}

	public void initDefaultCommand() {
		this.setDefaultCommand(new ShootByJoystick());
	}

	public void invertPower() {
		isPowered = !isPowered;
	}

	@Override
	public void periodic() {
		if (isPowered) {
			new ShootByPower(0.3).schedule();
		} else {
			new ShootByPower(0).schedule();
		}
	}
}
