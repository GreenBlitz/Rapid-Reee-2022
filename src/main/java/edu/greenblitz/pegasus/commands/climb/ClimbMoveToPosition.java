package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ClimbMoveToPosition extends ParallelCommandGroup {

	MoveRailToPosition extend;
	MoveTurningToAngle turning;

	public ClimbMoveToPosition(ClimbState state) {
		super();
		turning = new MoveTurningToAngle(state);
		extend = new MoveRailToPosition(state);
		addCommands(extend, turning);
	}

	@Override
	public boolean isFinished() {
		return super.isFinished();
	}
}
