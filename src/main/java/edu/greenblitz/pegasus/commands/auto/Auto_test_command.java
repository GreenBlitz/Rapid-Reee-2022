package edu.greenblitz.pegasus.commands.auto;


import com.pathplanner.lib.*;

import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.Localizer;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.utils.cords.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Auto_test_command {
    public Auto_test_command (String autoName){
        PathPlannerCommand autonomousCommand = new PathPlannerCommand(PathPlanner.loadPath(
                autoName,
                new PathConstraints(
                        RobotMap.Pegasus.Swerve.MAX_VELOCITY,
                        RobotMap.Pegasus.Swerve.KMMaxAcceleration
                ))
        );

        HashMap<String, GBCommand> eventMap = new HashMap<>();
        /** the event name, the command() */
        eventMap.put("shoot", new ShooterByRPM(20));

        SwerveAutoBuilder autoBuilder;
        autoBuilder = new SwerveAutoBuilder(
            SwerveChassis.getInstance()::getLocation,
            SwerveChassis.getInstance()::resetLocalizer,
                SwerveChassis.getInstance().getKinematics(),
                new PIDConstants(
                        RobotMap.Pegasus.Swerve.linPID.getKp(),
                        RobotMap.Pegasus.Swerve.linPID.getKi(),
                        RobotMap.Pegasus.Swerve.linPID.getKd()
                ),
                new PIDConstants(
                        RobotMap.Pegasus.Swerve.rotationPID.getKp(),
                        RobotMap.Pegasus.Swerve.rotationPID.getKi(),
                        RobotMap.Pegasus.Swerve.rotationPID.getKd()
                ),
                SwerveChassis.getInstance().getModuleStates(),
                eventMap,
                SwerveChassis.getInstance()
        );


    }
}