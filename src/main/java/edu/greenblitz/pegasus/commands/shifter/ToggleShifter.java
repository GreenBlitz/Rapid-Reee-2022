package edu.greenblitz.pegasus.commands.shifter;

import edu.greenblitz.gblib.gears.Gear;
import edu.greenblitz.pegasus.subsystems.Shifter;

public class ToggleShifter extends ShifterCommand{
	@Override
	public void initialize() {
		if(Shifter.getInstance().getCurrentGear() == Gear.POWER) {
			new ToSpeed().schedule();
		} else {
			new ToPower().schedule();
		}
	}
}
