package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullClimb extends SequentialCommandGroup {
	public FullClimb(SmartJoystick joystick){
		addCommands(
				new SafeExitStartCondition(), // temp for testing should be first thing on climb in auto init
				new ClimbMoveToPosition(ClimbState.PULL_UP),
				new HybridPullDown(joystick));
	}
}

