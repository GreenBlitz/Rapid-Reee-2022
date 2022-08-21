package edu.greenblitz.pegasus.commands.shifter;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.greenblitz.pegasus.subsystems.Shifter;

public class ShifterCommand extends GBCommand {
	Shifter shifter;

	public ShifterCommand() {
		this.shifter = RobotContainer.getInstance().getShifter();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
