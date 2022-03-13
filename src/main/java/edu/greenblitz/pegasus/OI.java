package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.threading.ThreadedCommand;
import edu.greenblitz.pegasus.commands.chassis.localizer.LocalizerCommand;
import edu.greenblitz.pegasus.commands.chassis.profiling.Follow2DProfileCommand;
import edu.greenblitz.pegasus.commands.chassis.test.CheckMaxLin;
import edu.greenblitz.pegasus.commands.chassis.test.CheckMaxRot;
import edu.greenblitz.pegasus.commands.climb.*;
import edu.greenblitz.pegasus.commands.climb.Rail.RailByJoystick;
import edu.greenblitz.pegasus.commands.climb.Turning.SwitchTurning;
import edu.greenblitz.pegasus.commands.climb.Turning.TurningByJoystick;
import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.indexing.PrintColor;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.*;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shifter.ToggleShifter;
import edu.greenblitz.pegasus.commands.shooter.*;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.Localizer;
import org.greenblitz.motion.base.State;

import java.util.ArrayList;
import java.util.Arrays;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private enum IOModes {
		DEBUG, REAL, NO_AUTO
	}
	
	private static final IOModes IOMode = IOModes.DEBUG; //decides which set of controls to init.
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
		mainJoystick.BACK.whenPressed(new InstantCommand(new Runnable() {
			@Override
			public void run() {
				Chassis.getInstance().resetGyro();
			}
		}));
		mainJoystick.START.whenPressed(new ToggleRoller());
		State start = new State(0,0,0,0,0);
		State end = new State(0,1.5,0,0,0);
		ArrayList<State> list = new ArrayList<>(Arrays.asList(start, end));
		mainJoystick.A.whenPressed(new ThreadedCommand(new Follow2DProfileCommand(list, RobotMap.Pegasus.Chassis.MotionData.CONFIG, 0.8, false)));
		Chassis.getInstance().initDefaultCommand(mainJoystick);
		Climb.getInstance().initDefaultCommand(secondJoystick);


	}
	private void initRealButtons() {
		secondJoystick.Y.whileHeld(new EjectFromShooter());

		secondJoystick.A.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM){

			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		secondJoystick.X.whileHeld(new InsertIntoShooter());

		secondJoystick.B.whileHeld(
				new ParallelCommandGroup(new HandleBalls(), new RollByConstant(1.0)
				)
		);
		secondJoystick.START.whenPressed(new ToggleRoller());
		secondJoystick.BACK.whenPressed(new HybridPullDown(secondJoystick));
//		secondJoystick.POV_LEFT.whenPressed(new InitManualOverride())\]]]]]]]]]]]]]]]]]]]]]][
//		;

		secondJoystick.R1.whileHeld(new WhileHeldCoast());

		secondJoystick.POV_UP.whenPressed(new FullClimb(secondJoystick));

		Climb.getInstance().initDefaultCommand(secondJoystick);

		secondJoystick.POV_RIGHT.whenPressed(new ClimbMoveToPosition(ClimbState.MID_GAME));
		secondJoystick.POV_DOWN.whenPressed(new ClimbMoveToPosition(ClimbState.START));

		secondJoystick.L1.whenPressed(new ParallelCommandGroup(
				new RailByJoystick(secondJoystick),
				new TurningByJoystick(secondJoystick)
		));
		

		mainJoystick.B.whileHeld(new SwitchTurning(mainJoystick, secondJoystick));
		mainJoystick.POV_LEFT.whileHeld(new WhileHeldCoast());

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
