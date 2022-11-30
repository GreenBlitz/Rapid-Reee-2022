package edu.greenblitz.pegasus.commands;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.Robot;
import edu.greenblitz.pegasus.subsystems.Battery;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BatteryDisabler extends GBCommand {

	private Battery battery;
	public static final int disableAfterTicks = 1;
	public int timesUnder;


	public BatteryDisabler (){
		Battery.isBatteryLow = false;
		battery = Battery.getInstance();
		require(battery);
	}

	@Override
	public void initialize() {
		timesUnder = 0;
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("disabkle by batter: ", Battery.isBatteryLow);
		SmartDashboard.putBoolean(" is disabled: ", 	DriverStation.isDisabled());
		
		SmartDashboard.putNumber("by battery voltage: ", Battery.getInstance().getCurrentVoltage());
		
		if(battery.getCurrentVoltage() < battery.getMinVoltage()){
			timesUnder++;
		}
		if (timesUnder >= disableAfterTicks &&
				(DriverStation.getMatchType() == DriverStation.MatchType.None ||
				DriverStation.getMatchType() == DriverStation.MatchType.Practice)){
			
			Battery.isBatteryLow = true;
			
			throw new java.lang.RuntimeException("Battery is low");
		}
	}
}
