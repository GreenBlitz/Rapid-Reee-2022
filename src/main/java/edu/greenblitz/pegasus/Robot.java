package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.auto.PathFollowerBuilder;
import edu.greenblitz.pegasus.commands.auto.PathPlannerCommand;
import edu.greenblitz.pegasus.commands.auto.Taxi;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.AutoSelector;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		
		DigitalInputMap.getInstance();
		Pneumatics.init();
		Dashboard.init();
		Funnel.getInstance();
		Intake.getInstance();
		Indexing.getInstance();
		Shooter.create(RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);
		AutoSelector.getInstance();

		//todo add voltage compensation
		//swerve
		
		SwerveChassis.getInstance().resetChassisAngle();
		SwerveChassis.getInstance().resetAllEncoders();
		SmartDashboard.putNumber("auto number", 1);

		OI.getInstance();
	}


	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		SmartDashboard.putNumber("pigeon angle", Math.toDegrees(SwerveChassis.getInstance().getChassisAngle()));
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


	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
	*/
	@Override
	public void autonomousInit() {
		//resets encoders
		SwerveChassis.getInstance().resetAllEncoders();
		SwerveChassis.getInstance().resetLocalizer();
//		PathFollowerBuilder.getInstance().followPath(AutoSelector.getInstance().getSelectedAuto());
		SmartDashboard.putString("dood", AutoSelector.getInstance().getSelectedAuto());

	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
