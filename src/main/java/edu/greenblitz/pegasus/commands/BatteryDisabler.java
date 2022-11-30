package edu.greenblitz.pegasus.commands;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.Robot;
import edu.greenblitz.pegasus.subsystems.Battery;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BatteryDisabler extends GBCommand {

	private Battery battery;
	
	private static final int LEN_OF_AVG = 10;
	
	// number of ticks of under voltage running
	double ticksUnder = 0;
	double maxTicksUnder = 50; //50 ticks = 1 second
	private LinearFilter voltageFilter;
	


	public BatteryDisabler (){
		voltageFilter = LinearFilter.movingAverage(LEN_OF_AVG);
		Battery.isBatteryLow = false;
		battery = Battery.getInstance();
		require(battery);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("disabkle by batter: ", Battery.isBatteryLow);
		SmartDashboard.putBoolean(" is disabled: ", 	DriverStation.isDisabled());
		
		SmartDashboard.putNumber("by battery voltage: ", Battery.getInstance().getCurrentVoltage());
		
		
		double a = voltageFilter.calculate(battery.getCurrentVoltage());
		SmartDashboard.putNumber("battery avarage: ", a );
		
		ticksUnder += a <= battery.getMinVoltage() ?  1 : 0;
		
		
		if (a <= battery.getMinVoltage()&& ticksUnder >= maxTicksUnder &&DriverStation.getMatchType() == DriverStation.MatchType.None){
			
			Battery.isBatteryLow = true;
			
			throw new java.lang.RuntimeException("Battery is low");
		}
	}
}
