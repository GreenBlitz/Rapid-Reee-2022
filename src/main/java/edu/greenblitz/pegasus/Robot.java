package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.chassis.localizer.LocalizerCommand;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
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
		Intake.getInstance();
		Shifter.getInstance();
		Funnel.getInstance();
		Shooter.init();
		Climb.getInstance().initDefaultCommand(OI.getInstance().getSecondJoystick());
		OI.getInstance();
		Indexing.getInstance();
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
		Chassis.getInstance().toCoast();
		new ToSpeed().schedule();
		new LocalizerCommand().schedule();
//		new ExtendRoller().schedule(); //TODO: remove before match!!!
		Indexing.getInstance().initSetAlliance();
		new SequentialCommandGroup(
				new WaitCommand(0.4),
			new ParallelRaceGroup(
				new WaitCommand(1),
				new MoveBallUntilClick()
			)
		).schedule();
	}
	
	@Override
	public void teleopPeriodic() {
	}
	
	
	@Override
	public void autonomousInit() {
		/*
//		Line Auto
		new ParallelRaceGroup(
				new WaitCommand(5),
				new RobotDotMove(-0.1) //auto line in the back of the robot
		).schedule();
		 */

		/*
		TODO: Dear @Orel, please for the love of god, use the very useful function: schedule(), this will help the
		the code to actually work
		*/
		//new ShootAndGo(5).schedule(); //Shoot and go
//		new FourBallAuto(0.3).schedule(); // 2 ball auto
	}
	
	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}
	
	@Override
	public void testPeriodic() {
	}
}
