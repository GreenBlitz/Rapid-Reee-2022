package edu.greenblitz.pegasus;


import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.subsystems.Chassis;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.*;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2
	}
	
	private static final IOModes IOMode = IOModes.DEBUG2; //decides which set of controls to init.
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
		mainJoystick.A.whileHeld(new ShootByConstant(0.3));
		Chassis.getInstance().setDefaultCommand(new ArcadeDrive(mainJoystick));
	}
	
	private void initDebugButtons() {
		mainJoystick.A.whenPressed(new DoubleShoot());
		mainJoystick.B.whileHeld(new RunFunnel());
		mainJoystick.Y.whileHeld(new RunRoller());
	}
	
	private void initRealButtons() {
		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());
		secondJoystick.A.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM) {
			
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		secondJoystick.X.whileHeld(new InsertIntoShooter());
		
		secondJoystick.B.whileHeld(
				new ParallelCommandGroup(new MoveBallUntilClick(), new RollByConstant(1.0))//new ParallelCommandGroup(new HandleBalls(), new RollByConstant(1.0))
		);
		
		secondJoystick.START.whenPressed(new ToggleRoller());
		secondJoystick.BACK.whenPressed(new EjectEnemyBallFromShooter());
		
		/*
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


		Climb.getInstance().initDefaultCommand(secondJoystick);

		secondJoystick.L1.whenPressed(new ParallelCommandGroup(
				new RailByJoystick(secondJoystick),
				new TurningByJoystick(secondJoystick)
		));
		
		Chassis.getInstance().setDefaultCommand(new ArcadeDrive(mainJoystick));

		mainJoystick.B.whileHeld(new SwitchTurning(mainJoystick, secondJoystick));
		mainJoystick.POV_LEFT.whileHeld(new WhileHeldCoast());
		mainJoystick.L1.whenPressed(new ToggleShifter());
		mainJoystick.A.whileHeld(new CoastWhileClimb());*/
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
		
		Chassis.getInstance().setDefaultCommand(new ArcadeDrive(mainJoystick));
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
