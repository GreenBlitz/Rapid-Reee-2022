package edu.greenblitz.pegasus;
//import edu.greenblitz.pegasus.commands.swerve.ModuleTest;
//import edu.greenblitz.pegasus.commands.swerve.MoveSingleByJoystick;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;

import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);
		IMotorFactory angFactory = new SparkMaxFactory().withGearRatio(6).withCurrentLimit(30).withRampRate(0.4);
		IMotorFactory linFactoryFR = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(40).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module1.INVERTED);
		IMotorFactory linFactoryFL = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(40).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module2.INVERTED);
		IMotorFactory linFactoryBR = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(40).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module3.INVERTED);
		IMotorFactory linFactoryBL = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(40).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module4.INVERTED);
		SwerveChassis.create(
				new SwerveModule(angFactory, linFactoryFR, RobotMap.Pegasus.Swerve.Module1.SteerMotorID, RobotMap.Pegasus.Swerve.Module1.linMotorID, RobotMap.Pegasus.Swerve.Module1.lampryID, RobotMap.Pegasus.Swerve.Module1.MAX_LAMPREY_VAL, RobotMap.Pegasus.Swerve.Module1.MIN_LAMPREY_VAL, RobotMap.Pegasus.Swerve.angPID, RobotMap.Pegasus.Swerve.linPID, feedforward),
				new SwerveModule(angFactory, linFactoryFL, RobotMap.Pegasus.Swerve.Module2.SteerMotorID, RobotMap.Pegasus.Swerve.Module2.linMotorID, RobotMap.Pegasus.Swerve.Module2.lampryID, RobotMap.Pegasus.Swerve.Module2.MAX_LAMPREY_VAL, RobotMap.Pegasus.Swerve.Module2.MIN_LAMPREY_VAL, RobotMap.Pegasus.Swerve.angPID, RobotMap.Pegasus.Swerve.linPID, feedforward),
				new SwerveModule(angFactory, linFactoryBR, RobotMap.Pegasus.Swerve.Module3.SteerMotorID, RobotMap.Pegasus.Swerve.Module3.linMotorID, RobotMap.Pegasus.Swerve.Module3.lampryID, RobotMap.Pegasus.Swerve.Module3.MAX_LAMPREY_VAL, RobotMap.Pegasus.Swerve.Module3.MIN_LAMPREY_VAL, RobotMap.Pegasus.Swerve.angPID, RobotMap.Pegasus.Swerve.linPID, feedforward),
				new SwerveModule(angFactory, linFactoryBL, RobotMap.Pegasus.Swerve.Module4.SteerMotorID, RobotMap.Pegasus.Swerve.Module4.linMotorID, RobotMap.Pegasus.Swerve.Module4.lampryID, RobotMap.Pegasus.Swerve.Module4.MAX_LAMPREY_VAL, RobotMap.Pegasus.Swerve.Module4.MIN_LAMPREY_VAL, RobotMap.Pegasus.Swerve.angPID, RobotMap.Pegasus.Swerve.linPID, feedforward),
				new PigeonGyro(new PigeonIMU(1)),
				RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates
		);
		SwerveChassis.getInstance().resetAllEncoders();
		OI.getInstance();
	}
	
	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("fr", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 180));
		SmartDashboard.putNumber("fl", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 180));
		SmartDashboard.putNumber("br", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 180));
		SmartDashboard.putNumber("bl", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 180));
		
		CommandScheduler.getInstance().run();

		SmartDashboard.putNumber("angle fr_right",Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.FRONT_RIGHT)));
		SmartDashboard.putNumber("angle fr_left",Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.FRONT_LEFT)));
		SmartDashboard.putNumber("angle back_right",Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.BACK_RIGHT)));
		SmartDashboard.putNumber("angle back_left",Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.BACK_LEFT)));
		
		SmartDashboard.putNumber("raw angle fr_right",SwerveChassis.getInstance().getRawLampreyAngle(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("raw angle fr_left",SwerveChassis.getInstance().getRawLampreyAngle(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("raw angle back_right",SwerveChassis.getInstance().getRawLampreyAngle(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("raw angle back_left",SwerveChassis.getInstance().getRawLampreyAngle(SwerveChassis.Module.BACK_LEFT));
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
