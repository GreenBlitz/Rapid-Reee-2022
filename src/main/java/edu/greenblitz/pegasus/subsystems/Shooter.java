package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;


public class Shooter extends GBSubsystem {
	private GBMotor motor;
	private static Shooter instance;

	private Shooter(){
		motor = new SparkMaxFactory().generate(RobotMap.Pegasus.Shooter.ShooterMotor.PORT);
	}
	public static Shooter getInstance(){
		if(instance == null){
			instance = new Shooter();
		}
		return instance;
	}
	public void shoot(double Power){
		motor.setPower(Power);
	}

	public double GetRPM(){
		return motor.getNormalizedVelocity();
	}


}
