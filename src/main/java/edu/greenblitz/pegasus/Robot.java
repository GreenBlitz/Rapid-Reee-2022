package edu.greenblitz.pegasus;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.pathplanner.lib.PathPlanner;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.greenblitz.pegasus.commands.auto.Taxi;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.Dashboard;
import edu.greenblitz.pegasus.subsystems.Indexing;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.commands.auto.ThreeBallAuto;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.swerve.PathFollowerCommand;
import edu.greenblitz.pegasus.commands.swerve.TragectoryCreator;

import edu.greenblitz.pegasus.subsystems.Pneumatics;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import java.util.ArrayList;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		
		DigitalInputMap.getInstance();
		Pneumatics.init();
		Dashboard.init();

		Indexing.getInstance();
		Shooter.create(new SparkMaxFactory().withInverted(true).withRampRate(0.4).withCurrentLimit(30), RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);
		PortForwarder.add(5800, "gloworm.local", 5800);
		PortForwarder.add(5801, "gloworm.local", 5801);
		PortForwarder.add(5802, "gloworm.local", 5802);
		PortForwarder.add(5803, "gloworm.local", 5803);
		PortForwarder.add(5804, "gloworm.local", 5804);
		PortForwarder.add(5805, "gloworm.local", 5805);
		//todo add voltage compensation
		//swerve
		SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);
		IMotorFactory angFactory = new SparkMaxFactory().withGearRatio(6).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.angleMotorInverted);
		IMotorFactory linFactoryFR = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module1.INVERTED);
		IMotorFactory linFactoryFL = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module2.INVERTED);
		IMotorFactory linFactoryBR = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module3.INVERTED);
		IMotorFactory linFactoryBL = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module4.INVERTED);
		
		SwerveModule frontRightModule = new SwerveModule(angFactory,
				linFactoryFR,
				RobotMap.Pegasus.Swerve.Module1.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module1.linMotorID,
				RobotMap.Pegasus.Swerve.Module1.lampryID,
				RobotMap.Pegasus.Swerve.Module1.MAX_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.Module1.MIN_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.angPID,
				RobotMap.Pegasus.Swerve.linPID,
				feedforward,
				RobotMap.Pegasus.Swerve.WHEEL_CIRC);
		SwerveModule frontLeftModule = new SwerveModule(angFactory,
				linFactoryFL,
				RobotMap.Pegasus.Swerve.Module2.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module2.linMotorID,
				RobotMap.Pegasus.Swerve.Module2.lampryID,
				RobotMap.Pegasus.Swerve.Module2.MAX_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.Module2.MIN_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.angPID,
				RobotMap.Pegasus.Swerve.linPID,
				feedforward,
				RobotMap.Pegasus.Swerve.WHEEL_CIRC);
		SwerveModule backRightModule = new SwerveModule(angFactory,
				linFactoryBR,
				RobotMap.Pegasus.Swerve.Module3.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module3.linMotorID,
				RobotMap.Pegasus.Swerve.Module3.lampryID,
				RobotMap.Pegasus.Swerve.Module3.MAX_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.Module3.MIN_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.angPID,
				RobotMap.Pegasus.Swerve.linPID,
				feedforward,
				RobotMap.Pegasus.Swerve.WHEEL_CIRC);
		SwerveModule backLeftModule = new SwerveModule(angFactory,
				linFactoryBL,
				RobotMap.Pegasus.Swerve.Module4.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module4.linMotorID,
				RobotMap.Pegasus.Swerve.Module4.lampryID,
				RobotMap.Pegasus.Swerve.Module4.MAX_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.Module4.MIN_LAMPREY_VAL,
				RobotMap.Pegasus.Swerve.angPID,
				RobotMap.Pegasus.Swerve.linPID,
				feedforward,
				RobotMap.Pegasus.Swerve.WHEEL_CIRC);
		
		SwerveChassis.create(
				frontRightModule, frontLeftModule, backRightModule, backLeftModule,
				new PigeonGyro(12),
				RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates,
				new Pose2d(0, 0, new Rotation2d(0)) //initial position of robot, 0 for now for testing
		);
		SwerveChassis.getInstance().resetChassisAngle(0); //fixme noam -  reminder for me if not work to check here

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
		SmartDashboard.putNumber("ang in deg", Math.toDegrees(SmartDashboard.getNumber("ang target", 0)));
		SmartDashboard.putNumber("ang dif", Math.toDegrees(SwerveChassis.getInstance().getChassisAngle() - SmartDashboard.getNumber("ang target", 0)));
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
		SwerveChassis.getInstance().resetAllEncodersByValues();
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
				(new Taxi(2.5, 2)).schedule(); //THIS   //todo sendablechooser for choosing auto



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
