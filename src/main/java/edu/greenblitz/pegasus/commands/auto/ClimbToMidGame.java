//package edu.greenblitz.pegasus.commands.auto;
//
//import edu.greenblitz.pegasus.commands.climb.ClimbMoveToPosition;
//import edu.greenblitz.pegasus.commands.climb.ClimbState;
//import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
//import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
//import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//
//import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;
//
//public class ClimbToMidGame extends SequentialCommandGroup {
//	public ClimbToMidGame(){
//		addCommands(
//			new MoveRailToPosition(0.613),
//			new MoveTurningToAngle(MID_START_ANGLE),
//			new ClimbMoveToPosition(ClimbState.MID_GAME)
//		);
//	}
//}
