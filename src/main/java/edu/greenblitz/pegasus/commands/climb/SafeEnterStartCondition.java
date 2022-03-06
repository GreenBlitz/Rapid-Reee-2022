package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SafeEnterStartCondition extends SequentialCommandGroup {
	public SafeEnterStartCondition(){
		addCommands(
				new MoveTurningToAngle(ClimbState.START),
				new MoveRailToPosition(ClimbState.START)
		);

	}
}
