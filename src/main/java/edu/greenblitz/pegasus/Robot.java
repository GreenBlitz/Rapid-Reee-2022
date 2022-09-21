package edu.greenblitz.pegasus;
//import edu.greenblitz.pegasus.commands.swerve.ModuleTest;
//import edu.greenblitz.pegasus.commands.swerve.MoveSingleByJoystick;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;

import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
//
//		IMotorFactory decoyFactory = new IMotorFactory() {
//			@Override
//			public GBMotor generate(int id) {
//				return new DecoyMotor();
//			}
//		};
//
//		IBrushedFactory brushedDecoy = new IBrushedFactory() {
//			@Override
//			public GBBrushedMotor generate(int id) {
//				return new DecoyMotor();
//			}
//		};



		CommandScheduler.getInstance().enable();
		DigitalInputMap.getInstance();
		SwerveChassis.create(
				new SwerveModule(new SparkMaxFactory(),new SparkMaxFactory(), RobotMap.Pegasus.Swerve.Module1.linMotorID,RobotMap.Pegasus.Swerve.Module1.SteerMotorID,RobotMap.Pegasus.Swerve.Module1.lampryID,RobotMap.Pegasus.Swerve.Module1.MAX_LAMPREY_VAL,RobotMap.Pegasus.Swerve.Module1.MIN_LAMPREY_VAL,RobotMap.Pegasus.Swerve.linPID,RobotMap.Pegasus.Swerve.angPID),
				new SwerveModule(new SparkMaxFactory(),new SparkMaxFactory(), RobotMap.Pegasus.Swerve.Module2.linMotorID,RobotMap.Pegasus.Swerve.Module2.SteerMotorID,RobotMap.Pegasus.Swerve.Module2.lampryID,RobotMap.Pegasus.Swerve.Module2.MAX_LAMPREY_VAL,RobotMap.Pegasus.Swerve.Module2.MIN_LAMPREY_VAL,RobotMap.Pegasus.Swerve.linPID,RobotMap.Pegasus.Swerve.angPID),
				new SwerveModule(new SparkMaxFactory(),new SparkMaxFactory(), RobotMap.Pegasus.Swerve.Module3.linMotorID,RobotMap.Pegasus.Swerve.Module3.SteerMotorID,RobotMap.Pegasus.Swerve.Module3.lampryID,RobotMap.Pegasus.Swerve.Module3.MAX_LAMPREY_VAL,RobotMap.Pegasus.Swerve.Module3.MIN_LAMPREY_VAL,RobotMap.Pegasus.Swerve.linPID,RobotMap.Pegasus.Swerve.angPID),
				new SwerveModule(new SparkMaxFactory(),new SparkMaxFactory(), RobotMap.Pegasus.Swerve.Module4.linMotorID,RobotMap.Pegasus.Swerve.Module4.SteerMotorID,RobotMap.Pegasus.Swerve.Module4.lampryID,RobotMap.Pegasus.Swerve.Module4.MAX_LAMPREY_VAL,RobotMap.Pegasus.Swerve.Module4.MIN_LAMPREY_VAL,RobotMap.Pegasus.Swerve.linPID,RobotMap.Pegasus.Swerve.angPID),
				new PigeonGyro(new PigeonIMU(1)),
				RobotMap.Pegasus.Swerve.SwerveLocations
		);
		OI.getInstance();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();

	}


	@Override
	public void disabledInit() {
		//VisionMaster.GameState.DISABLED.setAsCurrent();
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();

		/*new ToSpeed().schedule();
		new ExtendRoller().schedule();
		Indexing.getInstance().initSetAlliance();
		Shooter.getInstance().setIdleMode(AbstractMotor.IdleMode.Coast);
		new SequentialCommandGroup(
				new ParallelRaceGroup(
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, 2300) {
							@Override
							public void end(boolean interrupted) {
								shooter.setSpeedByPID(0);
							}
						},
						new MoveBallUntilClick(),
						new WaitCommand(3)
				)
		);//.schedule();*/
	}

	@Override
	public void teleopPeriodic() {
	}


	/*
		TODO: Dear @Orel, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
	*/
	@Override
	public void autonomousInit() {

		//Climb.getInstance().resetTurningMotorTicks();
		//Climb.getInstance().resetRailMotorTicks();
		//new DCMPAuto().schedule();
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
