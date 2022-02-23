package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.colorSensor.PrintColor;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.greenblitz.pegasus.utils.VisionMaster;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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
		OI.getInstance();

//        VisionMaster.getInstance().register();
		Chassis.getInstance();
*/		// Must be last!
		ColorSensor.getInstance();

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
		new PrintColor().schedule();
	}

	@Override
	public void teleopPeriodic() {
	}



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
