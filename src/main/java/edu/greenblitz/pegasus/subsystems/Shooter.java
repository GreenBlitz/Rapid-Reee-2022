package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.GBSparkMax;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;

public class Shooter extends GBSubsystem {

	private GBMotor motor;
	private static Shooter instance;

	private Shooter(){
		this.motor = new GBSparkMax(RobotMap.Pegasus.Shooter.ShooterMotor.PORT);
	}

	public static Shooter getInstance()
	{
		if(instance == null)
		{
			instance = new Shooter();
		}
		return instance;
	}

	public void SetPower(double power) {
		motor.setPower(power);
	}
}
