package edu.greenblitz.pegasus.commands;

import edu.greenblitz.pegasus.subsystems.Battery;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BatteryDisabler extends GBCommand {

	private Battery battery;
	
	private static final int LEN_OF_AVG = 10;
	private LinearFilter voltageFilter;
	


	public BatteryDisabler (){
		voltageFilter = LinearFilter.movingAverage(LEN_OF_AVG);
		battery = Battery.getInstance();
		require(battery);
	}

	@Override
	public void initialize() {
		for (int i = 0; i < LEN_OF_AVG; i++) {
			voltageFilter.calculate(battery.getCurrentVoltage());
		}
	}

	@Override
	public void execute() {
		SmartDashboard.putNumber("by battery voltage: ", Battery.getInstance().getCurrentVoltage());
		
		
		double currentAverageVoltage = voltageFilter.calculate(battery.getCurrentVoltage());

		if (currentAverageVoltage <= battery.getMinVoltage()&&DriverStation.getMatchType() == DriverStation.MatchType.None){

			throw new java.lang.RuntimeException("Battery is low");
		}
	}
}
