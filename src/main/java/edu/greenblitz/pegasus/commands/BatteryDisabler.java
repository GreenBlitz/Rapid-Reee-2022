package edu.greenblitz.pegasus.commands;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Battery;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class BatteryDisabler extends GBCommand {

	private boolean under;
	private Battery battery;

	public BatteryDisabler (){
		battery = new Battery();
		require(battery);
	}

	@Override
	public void initialize() {
		under = false;
	}

	@Override
	public void execute() {
		if(battery.getCurrentVoltage() < battery.getMinVoltage()){
			under = true;
		}
		if (under){
			CommandScheduler.getInstance().cancelAll();
			CommandScheduler.getInstance().disable();
		}
	}
}
