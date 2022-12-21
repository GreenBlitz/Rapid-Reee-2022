package edu.greenblitz.pegasus.commands.auto;


import com.pathplanner.lib.*;

import com.pathplanner.lib.auto.SwerveAutoBuilder;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.intake.roller.StopRoller;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.HashMap;

public class PathFollowerBuilder extends SwerveAutoBuilder{
	
	private static final HashMap<String, Command> eventMap = new HashMap<>();
	static {
		// the event name, the command()
		eventMap.put("ShooterByRPM300", new ShooterByRPM(300));
		eventMap.put("FullShoot300", new ShooterByRPM(300).alongWith(new InsertIntoShooter()));
		eventMap.put("StopShooter", new StopShooter());
		eventMap.put("ExtendAndCollect", new ExtendAndCollect());
		eventMap.put("StopRoller", new StopRoller());
	}
	
	private static PathFollowerBuilder instance;
	private PathFollowerBuilder() {
		super(SwerveChassis.getInstance()::getLocation,
				SwerveChassis.getInstance()::resetLocalizer,
				SwerveChassis.getInstance().getKinematics(),
				RobotMap.Pegasus.Swerve.linPID.getPIDConst(),
				RobotMap.Pegasus.Swerve.rotationPID.getPIDConst(),
				SwerveChassis.getInstance()::setModuleStates,
				eventMap,
				SwerveChassis.getInstance()
		);
	}
	
	
	public static PathFollowerBuilder getInstance() {
		if (instance == null) {
			instance = new PathFollowerBuilder();
		}
		return instance;
	}
	
	
	public CommandBase followPath(String pathName) {
		
		return followPath(PathPlanner.loadPath(
				pathName,
				new PathConstraints(
						RobotMap.Pegasus.Swerve.MAX_VELOCITY,
						RobotMap.Pegasus.Swerve.KMMaxAcceleration
				))
		);
	}

    }