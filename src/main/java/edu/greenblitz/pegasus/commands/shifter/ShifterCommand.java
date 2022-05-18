package edu.greenblitz.pegasus.commands.shifter;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shifter;

public class ShifterCommand extends GBCommand {
	Shifter shifter;
	
	public ShifterCommand(){
		this.shifter = Shifter.getInstance();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
