package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ToggleClimbPosition extends InstantCommand {


	@Override
	protected void initialize() {
		if (Climb.getInstance().getAtStart()){
			new SafeExitStartCondition().schedule();
		}
		else{
			new SafeEnterStartCondition().schedule();
		}
	}
}
