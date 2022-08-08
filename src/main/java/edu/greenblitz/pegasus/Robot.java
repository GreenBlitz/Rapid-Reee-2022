package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.auto.DCMPAuto;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.util.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
//		DigitalInputMap.getInstance();
//		Intake.getInstance();
//		Shifter.getInstance();
//		Funnel.getInstance();
//		Shooter.init();
//		Climb.getInstance().initDefaultCommand(OI.getInstance().getSecondJoystick());
		OI.getInstance();
//		Indexing.getInstance();
		PortForwarder.add(5800, "limelight.local", 5800);
		PortForwarder.add(5801, "limelight.local", 5801);
		PortForwarder.add(5802, "limelight.local", 5802);
		PortForwarder.add(5803, "limelight.local", 5803);
		PortForwarder.add(5804, "limelight.local", 5804);
		PortForwarder.add(5805, "limelight.local", 5805);
		Chassis.getInstance(); // Must be last!
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
		Shooter.getInstance().toCoast();
		new SequentialCommandGroup(
				new ParallelRaceGroup(
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 2300) {
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
		Chassis.getInstance().toBrake();
		Chassis.getInstance().resetGyro();
		Climb.getInstance().resetTurningMotorTicks();
		Climb.getInstance().resetRailMotorTicks();
		//new StopShooter().schedule();
		new DCMPAuto().schedule();
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
