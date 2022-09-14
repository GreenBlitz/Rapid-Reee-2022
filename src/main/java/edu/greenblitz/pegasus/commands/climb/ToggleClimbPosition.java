package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.subsystems.Climb;

public class ToggleClimbPosition implements Runnable {

	public ToggleClimbPosition() {
		super();
	}


	@Override
	public void run() {
		{
			if (Climb.getInstance().getAtStart()) {
				new SafeExitStartCondition().schedule();
			} else {
				new SafeEnterStartCondition().schedule();
			}
		}
	}
}
