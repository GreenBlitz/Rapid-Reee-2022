package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.chassis.LineAuto;

import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();

		DigitalInputMap.getInstance();


		//Pneumatics.init();
/*		Intake.getInstance();
		//Shifter.init();
		Funnel.getInstance();
		Shooter.init();
		ComplexClimb.getInstance();
>>>>>>> 910e430 (asaf- set up color sensor test)
		OI.getInstance();
		Pneumatics.init();

//        VisionMaster.getInstance().register();
		Chassis.getInstance();
*/		// Must be last!
		BallQueue.getInstance();

	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void teleopInit() {
		CommandScheduler.getInstance().cancelAll();
	}
	@Override
	public void teleopPeriodic() {
	}



	@Override
	public void autonomousInit() {
		new ParallelRaceGroup(
				new WaitCommand(1),
				new LineAuto(-0.1) //auto line in the back of the robot
		).schedule();
}


	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
