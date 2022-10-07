package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

import java.util.List;

public class PathFollowerCommand extends SwerveCommand {
	
	// 1. Create trajectory settings
	TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
			AutoConstants.kMaxSpeedMetersPerSecond,
			AutoConstants.kMaxAccelerationMetersPerSecondSquared)
			.setKinematics(DriveConstants.kDriveKinematics);
	
	// 2. Generate trajectory
	Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
			new Pose2d(0, 0, new Rotation2d(0)),
			List.of(
					new Translation2d(1, 0),
					new Translation2d(1, -1)),
			new Pose2d(2, -1, Rotation2d.fromDegrees(180)),
			trajectoryConfig);
	
	// 3. Define PID controllers for tracking trajectory
	PIDController xController = new PIDController(0.3,0,0);
	PIDController yController = new PIDController(0.3,0,0);
	ProfiledPIDController thetaController = new ProfiledPIDController(
			xController, 0, 0, yController;
		thetaController.enableContinuousInput(-Math.PI, Math.PI);
	
	// 4. Construct command to follow trajectory
	SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
			trajectory,
			swerveSubsystem::getPose,
			DriveConstants.kDriveKinematics,
			xController,
			yController,
			thetaController,
			swerveSubsystem::setModuleStates,
			swerveSubsystem);
	
	
	
	
	
	// 5. Add some init and wrap-up, and return everything
		return new SequentialCommandGroup(
				new InstantCommand(() -> swerveSubsystem.resetOdometry(trajectory.getInitialPose())),
				swerveControllerCommand,
				new InstantCommand(() -> swerveSubsystem.stopModules()));
	
}
