package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;
import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.commands.ShootByJoystick;
import edu.greenblitz.pegasus.commands.ShootByPower;

public class Shooter extends GBSubsystem {

	private static Shooter instance;
	private GBBrushedMotor motor;
	private boolean isOn = false;

	private Shooter() {
		this.motor = new TalonSRXFactory().generate(3);
	}

	public static Shooter getInstance(){
		if(instance==null){
			instance = new Shooter();
		}
		return instance;
	}

	public void setPower (double power){
		motor.setPower(power);
	}

	public void toggle(){
		isOn = !isOn;
	}

	@Override
	public void periodic() {
		if(isOn){
			new ShootByPower(0.7).schedule();
		}
//		else{
//			new ShootByPower(0).schedule();
//		}
	}
}
