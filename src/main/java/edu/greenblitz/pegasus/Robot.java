package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.BatteryDisabler;
import edu.greenblitz.pegasus.subsystems.Battery;
import edu.greenblitz.pegasus.subsystems.Dashboard;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.AutoSelector;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();
		Pneumatics.init();
		Dashboard.init();
		Limelight.getInstance();
		Funnel.getInstance();
		Intake.getInstance();
		Indexing.getInstance();
//		PortForwarder.add(5800, "gloworm.local", 5800);
//		PortForwarder.add(5801, "gloworm.local", 5801);
//		PortForwarder.add(5802, "gloworm.local", 5802);
//		PortForwarder.add(5803, "gloworm.local", 5803);
//		PortForwarder.add(5804, "gloworm.local", 5804);
//		PortForwarder.add(5805, "gloworm.local", 5805);
		Shooter.create(RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);

		LiveWindow.disableAllTelemetry();
		Battery.getInstance().setDefaultCommand(new BatteryDisabler());
		AutoSelector.getInstance();
		
		//swerve

		SwerveChassis.getInstance().resetChassisAngle(0);
		SwerveChassis.getInstance().resetAllEncoders();
		SmartDashboard.putNumber("auto number", 1);
		OI.getInstance();
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


	/*
		TODO: Dear @Orel & @Tal, please for the love of god, use the very useful function: schedule(), this will help the code to actually work
	*/
	@Override
	public void autonomousInit() {
		//resets encoders
		SwerveChassis.getInstance().resetAllEncoders();
//		SwerveChassis.getInstance().resetChassisPose();
//		PathFollowerBuilder.getInstance().followPath(AutoSelector.getInstance().getSelectedAuto()).schedule();
//		SmartDashboard.putString("dood", AutoSelector.getInstance().getSelectedAuto());

	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
