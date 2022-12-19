package edu.greenblitz.pegasus.subsystems;

import edu.wpi.first.wpilibj.RobotController;

import java.sql.Driver;

public class Battery extends GBSubsystem {

	private double currentVoltage;
	private static final double minVoltage = 12.50;
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
