package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.Chassis.Chassis;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		DigitalInputMap.getInstance();
//		RobotContainer.getInstance().getClimb().initDefaultCommand(OI.getInstance().getSecondJoystick());
//		RobotContainer.getInstance().getChassis().setDefaultCommand(new ArcadeDrive(OI.getInstance().getMainJoystick()));
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
		RobotContainer.getInstance().getIndexing().initSetAlliance();
		RobotContainer.getInstance().getShooter().setIdleMode(AbstractMotor.IdleMode.Coast);
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
		RobotContainer.getInstance().getChassis().setIdleMode(AbstractMotor.IdleMode.Brake);
		RobotContainer.getInstance().getChassis().resetGyro();
		//Climb.getInstance().resetTurningMotorTicks();
		//Climb.getInstance().resetRailMotorTicks();
		new StopShooter().schedule();
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
