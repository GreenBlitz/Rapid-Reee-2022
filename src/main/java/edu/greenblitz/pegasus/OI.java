package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.auto.MoveAngleByPID;
import edu.greenblitz.pegasus.commands.auto.MoveLinearByPID;
import edu.greenblitz.pegasus.commands.chassis.WhileHeldChassisCoast;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import edu.greenblitz.pegasus.commands.chassis.driver.SmoothArcadeDrive;
import edu.greenblitz.pegasus.commands.climb.*;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Rail.RailByJoystick;
import edu.greenblitz.pegasus.commands.climb.Rail.RailToSecondBar;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.commands.climb.Turning.SwitchTurning;
import edu.greenblitz.pegasus.commands.climb.Turning.TurningByJoystick;
import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.indexing.PrintColor;
import edu.greenblitz.pegasus.commands.indexing.PrintRGB;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.multiSystem.*;
import edu.greenblitz.pegasus.commands.shifter.ToggleShifter;
import edu.greenblitz.pegasus.commands.shooter.*;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2
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
			case DEBUG2:
				initDebug2Buttons();
				break;
			
		}
	}
	
	private void initDebug2Buttons() {
		Chassis.getInstance().setDefaultCommand(new SmoothArcadeDrive(mainJoystick));
		mainJoystick.R1.whileHeld(new ArcadeDrive(mainJoystick));
		mainJoystick.START.whenPressed(new ToggleShifter());




	}
	
	private void initDebugButtons() {
//		Chassis.getInstance().initDefaultCommand(mainJoystick);
		Climb.getInstance().initDefaultCommand(secondJoystick);
		mainJoystick.BACK.whenPressed(new ToggleRoller());
		mainJoystick.START.whenPressed(new ToggleShifter());
		mainJoystick.Y.whenPressed(new InstantCommand(() -> Chassis.getInstance().resetGyro()));
		mainJoystick.X.whileHeld(new DoubleShoot(4500));
		mainJoystick.B.whenPressed(new RailToSecondBar());
		secondJoystick.POV_DOWN.whenPressed(new ClimbMoveToPosition(ClimbState.START));
		secondJoystick.POV_RIGHT.whenPressed(
				new SequentialCommandGroup(
						new MoveRailToPosition(0.613),
						new MoveTurningToAngle(MID_START_ANGLE),
						new ClimbMoveToPosition(ClimbState.MID_GAME)
				));
		secondJoystick.POV_UP.whenPressed(new ExtendFully());
		mainJoystick.A.whileHeld(new PrintColor());
		mainJoystick.B.whileHeld(new PrintRGB());

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
				new ParallelCommandGroup(new HandleBalls(), new RollByConstant(1.0))
		);
		
		secondJoystick.START.whenPressed(new ToggleRoller());
		secondJoystick.BACK.whenPressed(new HybridPullDown(secondJoystick));

		secondJoystick.POV_UP.whenPressed(new ExtendFully());
		secondJoystick.POV_RIGHT.whenPressed(
				new SequentialCommandGroup(
						new MoveRailToPosition(0.613),
						new MoveTurningToAngle(MID_START_ANGLE),
						new ClimbMoveToPosition(ClimbState.MID_GAME)
				));		secondJoystick.POV_DOWN.whenPressed(new ClimbMoveToPosition(ClimbState.START));

		secondJoystick.R1.whileHeld(new WhileHeldCoast());


		Climb.getInstance().initDefaultCommand(secondJoystick);

		secondJoystick.L1.whenPressed(new ParallelCommandGroup(
				new RailByJoystick(secondJoystick),
				new TurningByJoystick(secondJoystick)
		));
		
		Chassis.getInstance().initDefaultCommand(mainJoystick);

		mainJoystick.B.whileHeld(new SwitchTurning(mainJoystick, secondJoystick));
		mainJoystick.POV_LEFT.whileHeld(new WhileHeldCoast());
		mainJoystick.L1.whenPressed(new ToggleShifter());
		mainJoystick.BACK.whileHeld(new WhileHeldChassisCoast());
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
