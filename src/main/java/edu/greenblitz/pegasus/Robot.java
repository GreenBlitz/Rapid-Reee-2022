package edu.greenblitz.pegasus;
//import edu.greenblitz.pegasus.commands.swerve.ModuleTest;
//import edu.greenblitz.pegasus.commands.swerve.MoveSingleByJoystick;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		DigitalInputMap.getInstance();
		SwerveChassis.create(
				new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 8, 14, 3, 4012, 10 ),
				new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 7, 16, 2, 4012, 10 ),
				new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 11, 13, 1, 4012, 10 ),
				new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 10, 15, 0, 4012, 10 ),
				new PigeonGyro(new PigeonIMU(1)), 50, 50);
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
