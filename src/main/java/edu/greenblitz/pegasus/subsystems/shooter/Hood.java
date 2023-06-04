package edu.greenblitz.pegasus.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

import java.awt.image.AreaAveragingScaleFilter;

public class Hood extends GBSubsystem {
	GBSparkMax hoodMotor;
	DigitalInput microSwitch;
	
	private static Hood instance;
	public static Hood getInstance(){
		if (instance == null){
			instance = new Hood();
		}
		return instance;
	}
	private Hood(){
		this.hoodMotor = new GBSparkMax(RobotMap.Pegasus.Shooter.Hood.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.microSwitch = new DigitalInput(RobotMap.Pegasus.Shooter.Hood.LIMIT_SWITCH);
	}
	
	public double getAngle(){
		return hoodMotor.getEncoder().getPosition();
	}
	
	public boolean isPressed(){
		return microSwitch.get();
	}
	
	public void setHoodAngle(double angle){
		hoodMotor.getPIDController().setReference(angle, CANSparkMax.ControlType.kPosition);
	}
	
	
	
	
	
	
}
