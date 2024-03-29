package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

import java.util.List;

public class PathFollowerCommand extends SwerveControllerCommand {//todo move to experimental branch
	public PathFollowerCommand(Trajectory trajectory){
		
		super(
				trajectory,
				SwerveChassis.getInstance()::getLocation,
				SwerveChassis.getInstance().getKinematics(),
				createPIDController(),
				createPIDController(),
				createThetaController(),
				SwerveChassis.getInstance()::setModuleStates,
				SwerveChassis.getInstance());
		System.out.println("starting auto");
	}
	
	private static ProfiledPIDController createThetaController() {
		ProfiledPIDController thetaController = new ProfiledPIDController(
				RobotMap.Pegasus.Swerve.rotationPID.getKp(), 0, 0, new TrapezoidProfile.Constraints(RobotMap.Pegasus.Swerve.KMaxAngularVelocity, RobotMap.Pegasus.Swerve.KMMaxAngularAcceleration));
		thetaController.enableContinuousInput(-Math.PI, Math.PI);
		return thetaController;
	}
	
	private static PIDController createPIDController() {
		return new PIDController(RobotMap.Pegasus.Swerve.linPID.getKp(), 0, 0);
	}
	
	
	
}

