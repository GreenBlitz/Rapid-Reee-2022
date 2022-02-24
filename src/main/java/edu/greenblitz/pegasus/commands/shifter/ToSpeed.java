package edu.greenblitz.pegasus.commands.shifter;

import edu.greenblitz.gblib.gears.Gear;

public class ToSpeed extends ShifterCommand{
	@Override
	public void initialize() {
		shifter.setShift(Gear.SPEED);
	}
}
