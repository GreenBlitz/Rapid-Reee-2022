package edu.greenblitz.pegasus.subsystems;

import edu.wpi.first.wpilibj.RobotController;

public class Battery extends GBSubsystem {

	private double currentVoltage;
	private static final double minVoltage = /*11.97*/11;
	private static Battery instance;


	private Battery (){}

	public static Battery getInstance(){
		if(instance == null){
			instance = new Battery();
		}
		return instance;
	}

	public double getCurrentVoltage() {
		return RobotController.getBatteryVoltage();
	}

	public double getMinVoltage(){
		return minVoltage;
	}
}
