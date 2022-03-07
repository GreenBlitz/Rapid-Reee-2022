package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.climb.*;
import edu.greenblitz.pegasus.commands.climb.Rail.RailByJoystick;
import edu.greenblitz.pegasus.commands.climb.Turning.TurningByJoystick;
import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.indexing.PrintColor;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.*;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.*;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private enum IOModes {
		DEBUG, REAL, NO_AUTO
	}
	
	private static final IOModes IOMode = IOModes.REAL; //decides which set of controls to init.
	private static boolean isHandled = true;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		switch (IOMode) {
			case DEBUG:
				initDebugButtons();
				break;
			case REAL:
				initRealButtons();
				break;
			case NO_AUTO:
				initNoAutoButtons();
				break;
			
		}
	}
	
	private void initNoAutoButtons() {
		Chassis.getInstance().initDefaultCommand(mainJoystick);
	}
	
	private void initDebugButtons() {
		Chassis.getInstance().initDefaultCommand(mainJoystick);
		secondJoystick.START.whenPressed(new FullClimb(secondJoystick));
		secondJoystick.BACK.whenPressed(new InstantCommand(new ToggleClimbPosition()));
		secondJoystick.R1.whileHeld(new WhileHeldCoast());
		secondJoystick.POV_UP.whenPressed(new ParallelCommandGroup(new TurningByJoystick(secondJoystick), new RailByJoystick(secondJoystick)));
		
	}
	private void initRealButtons() {
		secondJoystick.Y.whileHeld(new DoubleShoot());

		secondJoystick.A.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 2750));
		secondJoystick.X.whileHeld(new InsertIntoShooter());

		secondJoystick.B.whileHeld(
				new ParallelCommandGroup(new HandleBalls(), new RollByConstant(1.0)) {
					@Override
					public void initialize() {
						new ToggleRoller().schedule();
					}

					@Override
					public void end(boolean interrupted) {
						new ToggleRoller().schedule();
					}
				}
		);
		
		secondJoystick.POV_UP.whenPressed(new ToggleRoller());
		secondJoystick.POV_DOWN.whileHeld(new EjectFromShooter());
		secondJoystick.POV_LEFT.whenPressed(new InitManualOverride());

		Climb.getInstance().initDefaultCommand(secondJoystick);

		secondJoystick.BACK.whenPressed(new InstantCommand(new ToggleClimbPosition()));

		secondJoystick.L1.whenPressed(new ParallelCommandGroup(
				new RailByJoystick(secondJoystick),
				new TurningByJoystick(secondJoystick)
		));
		
		Chassis.getInstance().initDefaultCommand(mainJoystick);
	}
	
	private class InitManualOverride extends GBCommand {
		
		private InitManualOverride() {
			super();
		}
		
		@Override
		public void initialize() {
			CommandScheduler.getInstance().cancelAll();
			super.initialize();

			secondJoystick.B.whileHeld(
					new ParallelCommandGroup(new RunFunnel(), new RollByConstant(1.0)) {
						@Override
						public void initialize() {
							new ToggleRoller().schedule();
						}

						@Override
						public void end(boolean interrupted) {
							new ToggleRoller().schedule();
						}
					}
			);

		}
		
		@Override
		public boolean isFinished() {
			return true;
		}
	}
	
	private void initTalButtons() {
		secondJoystick.X.whenPressed(new SequentialCommandGroup(new ParallelRaceGroup(new WaitCommand(1), new ShootByConstant(0.7)), new ParallelRaceGroup(new WaitCommand(4), new ShootByConstant(0.7), new RunFunnel())));
		secondJoystick.B.whenPressed(new SequentialCommandGroup(new ParallelRaceGroup(new WaitCommand(1), new ShootByConstant(0.4)), new ParallelRaceGroup(new WaitCommand(4), new ShootByConstant(0.4), new RunFunnel())));
		secondJoystick.Y.whileHeld(new ParallelCommandGroup(new ReverseRunFunnel(), new RollByConstant(-0.8)));
		secondJoystick.R1.whileHeld(new RollByConstant(0.8));
		secondJoystick.L1.whileHeld(new RollByConstant(-0.8));
		secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());
		
		Chassis.getInstance().initDefaultCommand(mainJoystick);
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}
	
	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
	
	public static boolean isIsHandled() {
		return isHandled;
	}
	
	public static void disableHandling() {
		isHandled = false;
	}
}
