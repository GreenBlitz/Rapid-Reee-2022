package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SafeExitStartCondition extends SequentialCommandGroup {
	public SafeExitStartCondition(){
		addRequirements(Climb.getInstance());
		
		addCommands(
				new MoveTurningToAngle(Climb.getInstance().getAng()+Math.toRadians(40)),
				new ParallelCommandGroup(
				new MoveRailToPosition(ClimbState.MID_GAME),
				new MoveTurningToAngle(ClimbState.MID_GAME)
		));
		
	}
}
