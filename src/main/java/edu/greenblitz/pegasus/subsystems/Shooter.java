package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByJoystick;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByPower;

public class Shooter extends GBSubsystem {
	private static Shooter instance;
	private CANSparkMax motor;
	private boolean isOn = false;

	private Shooter() {
		this.motor = new CANSparkMax(7, CANSparkMaxLowLevel.MotorType.kBrushless);
	}

	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}
		return instance;
	}

	public void setPower(double power) {
		motor.set(power);
	}

	public void InitDefaultCommand() {
		instance.setDefaultCommand(new ShootByJoystick());
	}

	public void toggle() {
		isOn = !isOn;
	}

	@Override
	public void periodic() {
		if (isOn){
			new ShootByPower(0.15).schedule();
		}
	}
}
