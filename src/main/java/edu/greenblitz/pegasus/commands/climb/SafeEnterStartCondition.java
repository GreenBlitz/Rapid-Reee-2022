package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SafeEnterStartCondition extends SequentialCommandGroup {
	public SafeEnterStartCondition(){
		addRequirements(Climb.getInstance());
		addCommands(
				new ParallelCommandGroup(new MoveTurningToAngle(ClimbState.START),
				new MoveRailToPosition(RobotMap.Pegasus.Climb.ClimbMotors.START_LOCATION-0.02)
				),
				new MoveRailToPosition(ClimbState.START)
		);

	}
}
