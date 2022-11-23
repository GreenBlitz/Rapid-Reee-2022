package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;

import java.sql.Driver;

public class Battery extends GBSubsystem {

	private double currentVoltage;
	private static final double minVoltage = 11.97;
	private static Battery instance;
	public static boolean isBatteryLow = false;


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
