package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Funnel extends GBSubsystem{
	private static Funnel instance;
	private WPI_TalonSRX motor;
	
	private Funnel() {
		super();
		motor = new WPI_TalonSRX(RobotMap.Pegasus.Funnel.FunnelMotor.MOTOR_PORT);
		motor.setInverted(RobotMap.Pegasus.Funnel.FunnelMotor.IS_REVERSED);
	}
	
	private static void init(){
		instance = new Funnel();
	}
	
	public static Funnel getInstance() {
		if(instance == null){
			init();
		}
		return instance;
	}

	public void moveMotor(double power){
		motor.set(power);
	}

	public void move(){
		motor.set(RobotMap.Pegasus.Funnel.POWER);
	}

	public void stop(){
		motor.set(0);
	}
}
