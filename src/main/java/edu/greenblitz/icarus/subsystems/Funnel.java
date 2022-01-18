package edu.greenblitz.icarus.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.icarus.RobotMap;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Funnel extends GBSubsystem{
	private static Funnel instance;
	private WPI_TalonSRX motor;
	
	private Funnel() {
		motor = new WPI_TalonSRX(RobotMap.Icarus.Funnel.FunnelMotor.MOTOR_PORT);
	}
	
	private static void init(){
		instance = new Funnel();
		CommandScheduler.getInstance().registerSubsystem(instance);
	}
	
	public static Funnel getInstance() {
		if(instance == null){
			init();
		}
		return instance;
	}
	
	public WPI_TalonSRX getMotor() {
		return motor;
	}
	
	public void moveMotor(double power){
		motor.set(power);
	}
	
	
	
	
}
