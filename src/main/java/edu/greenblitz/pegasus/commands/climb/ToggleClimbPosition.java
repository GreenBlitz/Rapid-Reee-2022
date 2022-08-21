package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public class ToggleClimbPosition implements Runnable {

	public ToggleClimbPosition() {
		super();
	}


	@Override
	public void run() {
		{
			if (RobotContainer.getInstance().getClimb().getAtStart()) {
				new SafeExitStartCondition().schedule();
			} else {
				new SafeEnterStartCondition().schedule();
			}
		}
	}
}
