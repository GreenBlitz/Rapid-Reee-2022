package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.commands.ShootByJoystick;
import edu.greenblitz.pegasus.commands.ShootByPower;

public class Shooter extends GBSubsystem {
private TalonSRX motor;
private static Shooter instance;
private boolean isOn;
private Shooter(){
	this.motor = new TalonSRX(3);
}
public static Shooter getInstance(){
	if (instance == null)
		instance = new Shooter();
	return instance;
}

public void initDefaultCommand(){
	setDefaultCommand(new ShootByJoystick());
}

public void setPower(double powah){
	motor.set(ControlMode.PercentOutput, powah);
}

public void toggle(){
	isOn = !isOn;
}
	
	
	@Override
	public void periodic() {
		if(isOn){
			new ShootByPower(0.2).schedule();
		}
	}
	
	
}
