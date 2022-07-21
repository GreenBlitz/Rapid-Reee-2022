package edu.greenblitz.pegasus;


import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		DigitalInputMap.getInstance();

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

	}

	@Override
	public void teleopPeriodic() {
	}


	/*
		TODO: Dear @Orel, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
	*/
	@Override
	public void autonomousInit() {
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
