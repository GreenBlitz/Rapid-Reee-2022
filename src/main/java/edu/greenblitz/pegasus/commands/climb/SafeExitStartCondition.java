package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SafeExitStartCondition extends SequentialCommandGroup {
	public SafeExitStartCondition() {
		addRequirements(RobotContainer.getInstance().getClimb());

		addCommands(
				new MoveTurningToAngle(RobotContainer.getInstance().getClimb().getAng() + Math.toRadians(40)),
				new ParallelCommandGroup(
						new MoveRailToPosition(ClimbState.MID_GAME),
						new MoveTurningToAngle(ClimbState.MID_GAME)
				));

	}
}
