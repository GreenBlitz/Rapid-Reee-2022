package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.GBMotor;
import edu.greenblitz.gblib.motors.GBSparkMax;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByJoystick;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByPower;

public class Shooter extends GBSubsystem {
	boolean isRunning = false;
	private GBMotor motor;
	private static Shooter instance;
	private Shooter() {
		motor = new GBSparkMax(11);
	}

	public static Shooter getInstance() {
		 if(instance == null){
			 instance = new Shooter();
		 }
		 return instance;
	}
	public void setPower(double power){
		motor.setPower(power);
	}

	public void initDefaultCommand(){
		this.setDefaultCommand(new ShootByJoystick());
	}

	public void toggle(){
		isRunning = !isRunning;
	}

	@Override
	public void periodic() {
		if (isRunning){
			new ShootByPower(0.3).schedule();
		}
	}
}
