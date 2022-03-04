package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.pegasus.subsystems.Indexing;

public class RollIfNotFull extends RunRoller{
	@Override
	public void execute() {
		if (Indexing.getInstance().getBallCount() != 2) {
			super.execute();
		}
	}
}
