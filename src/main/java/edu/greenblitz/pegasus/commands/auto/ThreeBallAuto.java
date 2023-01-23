//package edu.greenblitz.pegasus.commands.auto;
//
//import com.pathplanner.lib.PathPlanner;
//import edu.greenblitz.pegasus.RobotMap;
//import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
//import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
//import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.geometry.Translation2d;
//import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//
//import java.util.ArrayList;
//
//public class ThreeBallAuto extends SequentialCommandGroup {//todo move to experimental branch
//
//	public ThreeBallAuto(){
//		addCommands(
//				new DoubleShoot(3000),
//				new ExtendRoller(),
//				new PathPlannerCommand(PathPlanner.loadPath("3 ball auto", RobotMap.Pegasus.Swerve.MAX_VELOCITY / 3, RobotMap.Pegasus.Swerve.MAX_ACCELERATION / 3))
//						.alongWith(new MoveBallUntilClick()),
//				new DoubleShoot(3000)
//		);
//	}
//}
