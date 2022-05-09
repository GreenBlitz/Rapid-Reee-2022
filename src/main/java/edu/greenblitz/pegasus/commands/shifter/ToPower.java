package edu.greenblitz.pegasus.commands.shifter;


import edu.greenblitz.gblib.gear.GearState;

public class ToPower extends ShifterCommand{
	@Override
	public void initialize() {
		shifter.setShift(GearState.POWER);
	}
}
