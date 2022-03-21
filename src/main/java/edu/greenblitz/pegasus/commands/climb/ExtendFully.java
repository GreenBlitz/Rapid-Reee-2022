package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_SECOND_BAR;

public class ExtendFully extends SequentialCommandGroup {
	
	public ExtendFully() {
		addCommands(
				new ParallelCommandGroup(
						new MoveRailToPosition(0.3),
						new MoveTurningToAngle(RADIANS_TO_SECOND_BAR + Math.toRadians(12))
				),
				new MoveRailToPosition(METERS_TO_SECOND_BAR),
				new MoveTurningToAngle(RADIANS_TO_SECOND_BAR)
		);
	}
	
}