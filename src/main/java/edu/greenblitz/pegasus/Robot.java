package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.subsystems.Dashboard;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		
		DigitalInputMap.getInstance();
//		Pneumatics.init();
		Dashboard.init();

//		Indexing.getInstance();
//		Shooter.create(RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);

		//todo add voltage compensation
		//swerve
		
		SwerveChassis.getInstance().resetChassisAngle();

//		SwerveChassis.getInstance().resetAllEncoders();
//		SwerveChassis.getInstance().resetAllEncodersByValues(); works
//		SwerveChassis.getInstance().resetChassisAngle();

//		SmartDashboard.putNumber("pigeon init value", 0);
		SmartDashboard.putNumber("auto number", 1);

		OI.getInstance();
	}


	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		SmartDashboard.putNumber("pigeon angle", Math.toDegrees(SwerveChassis.getInstance().getChassisAngle()));
	}


	@Override
	public void disabledInit() {
		//VisionMaster.GameState.DISABLED.setAsCurrent();
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();
		
	}

	@Override
	public void teleopPeriodic() {
	}


	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
	*/
	@Override
	public void autonomousInit() {
		//resets encoders
		SwerveChassis.getInstance().resetAllEncoders();
		SwerveChassis.getInstance().resetLocalizer();
		//				SwerveChassis.getInstance().resetChassisAngle(/*88.5*/-90);

		/**old auto
		new ExtendRoller()
				.andThen(new DoubleShoot(3500))
				.andThen(new ParallelDeadlineGroup(new Taxi(3, 3),new MoveBallUntilClick()))
				.andThen(new ParallelDeadlineGroup(new Taxi(3,-3),new MoveBallUntilClick()))
				.andThen(new DoubleShoot(3500)).schedule();
		**/

		//new auto for alliance
//		new ExtendRoller()
//				.andThen(new DoubleShoot(3500))
//				.andThen



		//				SwerveChassis.getInstance().resetChassisAngle(/*46.5*/-47);
		//				SwerveChassis.getInstance().resetChassisAngle(/*1.5*/-0);
		//				SwerveChassis.getInstance().resetChassisAngle(/*43.5*/-315);
		//		new PathFollowerCommand(new TragectoryCreator(new ArrayList<Translation2d>(0),new Pose2d(2,0,new Rotation2d())).generate()).schedule();
//		new PathFollowerCommand(PathPlanner.loadPath("New New Path", RobotMap.Pegasus.Swerve.KMaxVelocity / 3, RobotMap.Pegasus.Swerve.KMMaxAcceleration / 3)).schedule();
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
