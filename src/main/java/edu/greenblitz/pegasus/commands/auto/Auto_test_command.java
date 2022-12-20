package edu.greenblitz.pegasus.commands.auto;


import com.pathplanner.lib.*;

import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.Localizer;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.utils.cords.Position;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import org.greenblitz.motion.pathing.Path;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Auto_test_command {
    public Auto_test_command (String autoName){;

        HashMap<String, Command> eventMap = new HashMap<>();
        /** the event name, the command() */
        eventMap.put("shoot", new ShooterByRPM(20));
		eventMap.put("grip", new ExtendAndCollect());
	
		SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder(
				SwerveChassis.getInstance()::getLocation,
				SwerveChassis.getInstance()::resetLocalizer,
				SwerveChassis.getInstance().getKinematics(),
				RobotMap.Pegasus.Swerve.linPID.getPIDConst(),
				RobotMap.Pegasus.Swerve.rotationPID.getPIDConst(),
				SwerveChassis.getInstance()::setModuleStates,
				eventMap,
				SwerveChassis.getInstance()
		);
		autoBuilder.followPath(PathPlanner.loadPath(
				autoName,
				new PathConstraints(
						RobotMap.Pegasus.Swerve.MAX_VELOCITY,
						RobotMap.Pegasus.Swerve.KMMaxAcceleration
				))
		);
		
		/**
		 *       Supplier<Pose2d> poseSupplier,
		 *       Consumer<Pose2d> resetPose,
		 *       SwerveDriveKinematics kinematics,
		 *       PIDConstants translationConstants,
		 *       PIDConstants rotationConstants,
		 *       Consumer<SwerveModuleState[]> outputModuleStates,
		 *       HashMap<String, Command> eventMap,
		 *       Subsystem... driveRequirements)
		 * */


    }
}