package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.chassis.auto.FourBallAuto;
import edu.greenblitz.pegasus.commands.chassis.auto.RobotDotMove;
import edu.greenblitz.pegasus.commands.chassis.auto.ShootAndGo;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.greenblitz.pegasus.utils.VisionMaster;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();

		DigitalInputMap.getInstance();
		Intake.getInstance();
		//Shifter.getInstance();
		//Funnel.getInstance();
		//Shooter.getInstance();
		//ComplexClimb.getInstance();

		OI.getInstance();
		Pneumatics.init();
		ColorSensor.getInstance();

//        VisionMaster.getInstance().register();
		Chassis.getInstance();
		// Must be last!

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
		// Line auto
		/*
		new ParallelRaceGroup(
				new WaitCommand(5),
				new RobotDotMove(-0.1) //auto line in the back of the robot
		).schedule();
		 */

		//Shoot and go
		new ShootAndGo(5).schedule();


		//new FourBallAuto(0.3).schedule(); // 2 ball auto
	}


	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
