package edu.greenblitz.pegasus.commands;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Battery;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BatteryDisabler extends GBCommand {

	private Battery battery;
	public static final int disableAfterTicks = 5;
	public int timesUnder;


	public BatteryDisabler (){
		battery = new Battery();
		require(battery);
	}

	@Override
	public void initialize() {
		timesUnder = 0;
		CommandScheduler.getInstance().enable(); //todo - is this need?
	}

	@Override
	public void execute() {
		if(battery.getCurrentVoltage() < battery.getMinVoltage()){
			timesUnder++;
		}
		if (timesUnder >= disableAfterTicks &&
				DriverStation.getMatchType() == DriverStation.MatchType.None ||
				DriverStation.getMatchType() == DriverStation.MatchType.Practice){
			CommandScheduler.getInstance().cancelAll();
			CommandScheduler.getInstance().disable();
		}
	}
}
