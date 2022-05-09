package edu.greenblitz.pegasus.commands.shifter;


import edu.greenblitz.gblib.gear.GearState;

public class ToSpeed extends ShifterCommand{
	@Override
	public void initialize() {
		shifter.setShift(GearState.SPEED);
	}
}
