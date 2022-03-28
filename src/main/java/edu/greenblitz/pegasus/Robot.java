package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.auto.*;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

public class Robot extends TimedRobot {

	private final SendableChooser<CommandBase> chooser = new SendableChooser<>();

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
		chooser.setDefaultOption("TwoBall", new TwoBallAuto());
		chooser.addOption("ThreeBall", new ThreeBallAuto());
		SmartDashboard.putData(chooser);
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
		new ToSpeed().schedule();
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
		);//.schedule();
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
		new StopShooter().schedule();
		new TwoBallAuto().schedule();
		//chooser.getSelected().schedule();
	}

	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
	}
}
