package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Spinner extends GBSubsystem{
	private static Spinner instance;
	private CANSparkMax sparkMax;
	public static Spinner getInstance() {
		if(instance == null){
			instance  = new Spinner();
		}
		return instance;
	}
	
	public Spinner(){
		sparkMax = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
	}
	
	public void set(double power){
		sparkMax.set(power);
	}
	
	public void stop(){
		sparkMax.stopMotor();
	}
	
}
