package edu.greenblitz.pegasus.commands.auto;


import com.pathplanner.lib.PathPlanner;
import edu.greenblitz.pegasus.RobotMap;

public class Auto_test_command extends PathPlannerCommand{
    private static final String autoName = "new.path";
    public Auto_test_command (){
        super(PathPlanner.loadPath(
                autoName,
                RobotMap.Pegasus.Swerve.KMaxAngularVelocity/3,
                RobotMap.Pegasus.Swerve.KMMaxAcceleration/3
                )
        );

    }
}
