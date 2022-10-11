package edu.greenblitz.pegasus.commands.auto;

import com.pathplanner.lib.PathPlanner;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.swerve.PathFollowerCommand;
import edu.greenblitz.pegasus.commands.swerve.TragectoryCreator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.ArrayList;

public class ThreeBallAuto extends SequentialCommandGroup {
	
	public ThreeBallAuto(){
		addCommands(
				new DoubleShoot(),
				new ExtendRoller(),
				new PathFollowerCommand(PathPlanner.loadPath("3 ball auto", RobotMap.Pegasus.Swerve.KMaxVelocity / 3, RobotMap.Pegasus.Swerve.KMMaxAcceleration / 3))
						.alongWith(new MoveBallUntilClick()),
				new DoubleShoot()
		);
	}
}
