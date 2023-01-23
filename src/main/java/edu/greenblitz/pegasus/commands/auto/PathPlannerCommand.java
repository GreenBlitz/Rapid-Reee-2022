//package edu.greenblitz.pegasus.commands.auto;
//
//import com.pathplanner.lib.PathPlanner;
//import com.pathplanner.lib.PathPlannerTrajectory;
//import edu.greenblitz.pegasus.RobotMap;
//import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
//import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.ProfiledPIDController;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Rotation2d;
//import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
//import edu.wpi.first.math.kinematics.SwerveModuleState;
//import edu.wpi.first.math.trajectory.Trajectory;
//import edu.wpi.first.math.trajectory.TrapezoidProfile;
//import edu.wpi.first.wpilibj2.command.Subsystem;
//import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
//
//import java.util.ArrayList;
//import java.util.function.Consumer;
//import java.util.function.Supplier;
//
//public class PathPlannerCommand extends SwerveControllerCommand {
//
//
//    public PathPlannerCommand(PathPlannerTrajectory trajectory) {
//        super(
//                trajectory,
//                SwerveChassis.getInstance()::getLocation,
//                SwerveChassis.getInstance().getKinematics(),
//                createPIDController(),
//                createPIDController(),
//                createThetaController(),
//                SwerveChassis.getInstance()::setModuleStates,
//                SwerveChassis.getInstance());
//        System.out.println("starting auto");
//    }
//
//
//    private static ProfiledPIDController createThetaController() {
//        ProfiledPIDController thetaController = new ProfiledPIDController(
//                RobotMap.Pegasus.Swerve.rotationPID.getKp(), 0, 0, new TrapezoidProfile.Constraints(RobotMap.Pegasus.Swerve.MAX_ANGULAR_SPEED, RobotMap.Pegasus.Swerve.MAX_ANGULAR_ACCELERATION));
//        thetaController.enableContinuousInput(-Math.PI, Math.PI);
//        return thetaController;
//    }
//
//
//    private static PIDController createPIDController() {
//        return new PIDController(RobotMap.Pegasus.Swerve.SdsSwerve.linPID.getKp(), 0, 0);
//    }
//}
//
