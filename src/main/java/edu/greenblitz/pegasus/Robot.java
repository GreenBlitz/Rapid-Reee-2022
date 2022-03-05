package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.chassis.auto.ShootAndGo;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.climb.ClimbByJoysticks;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		DigitalInputMap.getInstance();
		Intake.getInstance();
		Shifter.getInstance();
		Funnel.getInstance();
		Shooter.init();
		Climb.getInstance().setDefaultCommand(new ClimbByJoysticks(OI.getInstance().getMainJoystick()));
		OI.getInstance();
		Indexing.getInstance().setDefaultCommand(new HandleBalls());
//      VisionMaster.getInstance().register();
//		ColorSensor.getInstance();
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
		
		new ShootAndGo(5).schedule(); //Shoot and go
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
