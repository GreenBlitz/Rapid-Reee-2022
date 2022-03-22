package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_SECOND_BAR;

public class ExtendFully extends SequentialCommandGroup {
	
	public ExtendFully() {
		addCommands(
				new ParallelCommandGroup(
						new MoveRailToPosition(METERS_TO_SECOND_BAR + 0.15),
						new MoveTurningToAngle(RADIANS_TO_SECOND_BAR + Math.toRadians(12))
				),
				new ParallelCommandGroup(
						new MoveRailToPosition(METERS_TO_SECOND_BAR),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> Climb.getInstance().getLoc() < 0.03),
								new MoveTurningToAngle(RADIANS_TO_SECOND_BAR)
						)
				)
		);
	}
	
}