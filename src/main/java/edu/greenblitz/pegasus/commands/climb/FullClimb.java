package edu.greenblitz.pegasus.commands.climb;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullClimb extends SequentialCommandGroup {
	public FullClimb(){
		addCommands(
				new ParallelCommandGroup(
						new MoveTurning(false),
						new MoveRail(false)
				),
				new ParallelCommandGroup(
						new MoveTurning(true),
						new MoveRail(true)
				),
				new BackAndForth(),
				new BackAndForth()
		);
	}
}
