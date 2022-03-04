package edu.greenblitz.pegasus.commands.climb;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class HangOnMid extends SequentialCommandGroup {
	/*
	1. position rail
	1. extend rail
	2. retract rail
	 */

	public HangOnMid() {
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
		ParallelCommandGroup extendClimb = new ParallelCommandGroup(new MoveTurningToAngle(false), new MoveRailToPosition(false));
		addCommands(extendClimb, new MoveRailToPosition(true));
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
	}

	@Override
	public boolean isFinished() {
		return super.isFinished();
	}
}
