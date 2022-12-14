//package edu.greenblitz.pegasus.commands.auto;
//
//
//import com.pathplanner.lib.PathPlanner;
//import com.pathplanner.lib.PathPlannerTrajectory;
//import edu.greenblitz.pegasus.RobotMap;
//import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
//import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
//import edu.greenblitz.pegasus.utils.Localizer;
//import edu.greenblitz.pegasus.utils.commands.GBCommand;
//import edu.greenblitz.pegasus.utils.cords.Position;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Auto_test_command extends PathPlannerCommand{
//    private static final String autoName = "new.path";
//    public Auto_test_command (){
//        ArrayList<PathPlannerTrajectory> pathGroup =
//                PathPlanner.loadPath(
//                        autoName,
//                        new PathConstrains(
//                                4/* m/s */,
//                                3 /* max acceleration of 3m/s ^ 2 */)
//                );
//
//        HashMap<String, GBCommand> eventMap = new HashMap<>();
//        /** the event name, the command() */
//        eventMap.put("shoot", new ShooterByRPM(20));
//        SwerveAutoBuilder autoBuilder = new SwerveAutoBuilder (
//                Localizer.getInstance().getLocation();
//                Localizer.getInstance().reset(
//                        0,
//                        0,
//                        new Position(0,0)),
//                SwerveChassis.getInstance().getKinematics(),
//                //new pid object
//                //new pid object
//
//                SwerveChassis.getInstance().setModuleStates(),
//                eventMap,
//                SwerveChassis.getInstance()
//        )
//
//
//    }
//}