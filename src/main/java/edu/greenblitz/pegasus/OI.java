package edu.greenblitz.pegasus;


import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import edu.greenblitz.pegasus.commands.climb.*;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Rail.RailByJoystick;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.commands.climb.Turning.SwitchTurning;
import edu.greenblitz.pegasus.commands.climb.Turning.TurningByJoystick;
import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToggleShifter;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.swerve.MoveByJoystick;
import edu.greenblitz.pegasus.commands.swerve.MoveLin;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.wpi.first.wpilibj2.command.*;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;

public class OI {
	private static final IOModes IOMode = IOModes.DEBUG2; //decides which set of controls to init.
	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;
	private final SmartJoystick secondJoystick;
	
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
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	public static boolean isIsHandled() {
		return isHandled;
	}
	
	public static void disableHandling() {
		isHandled = false;
	}
	
	private void initDebug2Buttons() {
		RobotContainer.getInstance().getSwerve().setDefaultCommand(new MoveByJoystick(mainJoystick));
	}
	
	private void initDebugButtons() {
		mainJoystick.A.whenPressed(new DoubleShoot());
		mainJoystick.B.whileHeld(new RunFunnel());
		mainJoystick.Y.whileHeld(new RunRoller());
	}
	
	private void initRealButtons() {
		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());
		secondJoystick.A.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.RPM) {
			
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setPower(0);
			}
		});
		secondJoystick.B.whileHeld(
				new ParallelCommandGroup(new MoveBallUntilClick(), new RollByConstant(1.0))//new ParallelCommandGroup(new HandleBalls(), new RollByConstant(1.0))
		);
		
		secondJoystick.START.whenPressed(new ToggleRoller());
		secondJoystick.BACK.whenPressed(new EjectEnemyBallFromShooter());
		
		
		secondJoystick.POV_UP.whenPressed(new FullClimb(secondJoystick));
		secondJoystick.POV_RIGHT.whenPressed(
				
				new SequentialCommandGroup(
						new MoveRailToPosition(0.613),
						new MoveTurningToAngle(MID_START_ANGLE),
						new ClimbMoveToPosition(ClimbState.MID_GAME)
				));
		secondJoystick.POV_DOWN.whenPressed(new ClimbMoveToPosition(ClimbState.START));
		
		secondJoystick.POV_LEFT.whenPressed(new ExtendFully());
		secondJoystick.R1.whileHeld(new WhileHeldCoast());
		
		
		RobotContainer.getInstance().getClimb().initDefaultCommand(secondJoystick);
		
		secondJoystick.L1.whenPressed(new ParallelCommandGroup(
				new RailByJoystick(secondJoystick),
				new TurningByJoystick(secondJoystick)
		));
		
		RobotContainer.getInstance().getChassis().setDefaultCommand(new ArcadeDrive(mainJoystick));
		
		mainJoystick.B.whileHeld(new SwitchTurning(mainJoystick, secondJoystick));
		mainJoystick.POV_LEFT.whileHeld(new WhileHeldCoast());
		mainJoystick.L1.whenPressed(new ToggleShifter());
	}
	
	private void initTalButtons() {
		secondJoystick.X.whenPressed(new SequentialCommandGroup(new ParallelRaceGroup(new WaitCommand(1), new ShootByConstant(0.7)), new ParallelRaceGroup(new WaitCommand(4), new ShootByConstant(0.7), new RunFunnel())));
		secondJoystick.B.whenPressed(new SequentialCommandGroup(new ParallelRaceGroup(new WaitCommand(1), new ShootByConstant(0.4)), new ParallelRaceGroup(new WaitCommand(4), new ShootByConstant(0.4), new RunFunnel())));
		secondJoystick.Y.whileHeld(new ParallelCommandGroup(new ReverseRunFunnel(), new RollByConstant(-0.8)));
		secondJoystick.R1.whileHeld(new RollByConstant(0.8));
		secondJoystick.L1.whileHeld(new RollByConstant(-0.8));
		secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());
		
		RobotContainer.getInstance().getChassis().setDefaultCommand(new ArcadeDrive(mainJoystick));
	}
	
	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}
	
	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2
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
}
