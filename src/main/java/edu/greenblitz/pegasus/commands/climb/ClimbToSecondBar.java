package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.RailToSecondBar;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class ClimbToSecondBar extends SequentialCommandGroup {
	
	private static final double SAFE_TO_ROTATE = 0.1;
	public ClimbToSecondBar(){
		addCommands(new ClimbMoveToPosition(ClimbState.MID_GAME),
				new ParallelCommandGroup(
						new RailToSecondBar(),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> Climb.getInstance().getLoc() < SAFE_TO_ROTATE),
								new MoveTurningToAngle(RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_SECOND_BAR)
						)
				)
		);
	}
}
