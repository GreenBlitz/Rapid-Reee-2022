//package edu.greenblitz.pegasus.commands.auto;
//
//
//import com.pathplanner.lib.PathConstraints;
//import com.pathplanner.lib.PathPlanner;
//import com.pathplanner.lib.auto.SwerveAutoBuilder;
//import edu.greenblitz.pegasus.RobotMap;
//import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
//import edu.greenblitz.pegasus.commands.intake.roller.StopRoller;
//import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
//import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
//import edu.greenblitz.pegasus.commands.shooter.StopShooter;
//import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.trajectory.Trajectory;
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.CommandBase;
//
//import java.util.HashMap;
//
//public class PathFollowerBuilder extends SwerveAutoBuilder {
//
//
//	/**
//	 * the use of the event map is to run a marker by its name run a command
//	 */
//	private static final HashMap<String, Command> eventMap = new HashMap<>();
//
//	static {
//		// the event name, the command()
//		eventMap.put("ShooterByRPM2000", new ShooterByRPM(2000));
//		eventMap.put("FullShoot2000", new ShooterByRPM(2000).alongWith(new InsertIntoShooter()));
//		eventMap.put("StopShooter", new StopShooter());
//		eventMap.put("ExtendAndCollect", new ExtendAndCollect());
//		eventMap.put("StopRoller", new StopRoller());
//	}
//
//	private static PathFollowerBuilder instance;
//
//	private PathFollowerBuilder() {
//		super(SwerveChassis.getInstance()::getLocation,
//				(Pose2d pose) -> SwerveChassis.getInstance().resetChassisPose(pose),
//				SwerveChassis.getInstance().getKinematics(),
//				RobotMap.Pegasus.Swerve.SdsSwerve.linPID.getPIDConst(),
//				RobotMap.Pegasus.Swerve.rotationPID.getPIDConst(),
//				SwerveChassis.getInstance()::setModuleStates,
//				eventMap,
//				SwerveChassis.getInstance()
//		);
//	}
//
//
//	public static PathFollowerBuilder getInstance() {
//		if (instance == null) {
//			instance = new PathFollowerBuilder();
//		}
//		return instance;
//	}
//
//
//	/**
//	 * @return returns the command for the full auto (commands and trajectory included)
//	 */
//	public CommandBase followPath(String pathName) {
//
//		return fullAuto(PathPlanner.loadPath(
//				pathName,
//				new PathConstraints(
//						2,
//						1
//				))
//		);
//	}
//
//	/**
//	 * get the WPILib trajectory object from a .path file
//	 */
//	public Trajectory getTrajectory(String trajectory) {
//		return PathPlanner.loadPath(trajectory,
//				0.5,
//				0.1);
//	}
//
//}