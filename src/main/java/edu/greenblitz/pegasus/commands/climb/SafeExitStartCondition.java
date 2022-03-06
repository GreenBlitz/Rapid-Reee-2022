package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SafeExitStartCondition extends SequentialCommandGroup {
	public SafeExitStartCondition(){
		addCommands(
				new MoveRailToPosition(ClimbState.MID_GAME),
				new MoveTurningToAngle(ClimbState.MID_GAME)
		);
		
	}
}
