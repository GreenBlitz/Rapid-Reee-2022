package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public class RollIfNotFull extends RunRoller {
	@Override
	public void execute() {
		if (RobotContainer.getInstance().getIndexing().getBallCount() != 2) {
			super.execute();
		}
	}
}
