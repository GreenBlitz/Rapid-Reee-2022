package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;

import java.util.List;

public class TragectoryCreator {
	private List<Translation2d> poses;
	private Pose2d finalPose;
	
	public TragectoryCreator(List<Translation2d> poses, Pose2d finalPose) {
		
		this.poses = poses;
		this.finalPose = finalPose;
		
		
	}
	
	
	public Trajectory generate(){
		// 1. Create trajectory settings
		TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
				RobotMap.Pegasus.Swerve.KMaxVelocity/3,
				RobotMap.Pegasus.Swerve.KMMaxAcceleration/3)
				.setKinematics(SwerveChassis.getInstance().getKinematics());
		
		// 2. Generate trajectory
		return TrajectoryGenerator.generateTrajectory(
				new Pose2d(0, 0, new Rotation2d(0)),
				poses, finalPose,
				trajectoryConfig);
	}
	
	
	
}


