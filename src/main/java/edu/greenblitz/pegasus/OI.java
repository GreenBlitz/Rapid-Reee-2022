package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.climb.*;
import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.indexing.PrintColor;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
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
		mainJoystick.R1.whileHeld(new ParallelCommandGroup(
				new HandleBalls(),
				new RunRoller()
		));
		/*
		mainJoystick.B.whileHeld(
				new ParallelCommandGroup(
						new InsertIntoShooter(),
					new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 2700) {
						
						@Override
					public void end(boolean interrupted) {
						super.end(interrupted);
						shooter.setSpeedByPID(0);
					}}
				)
		);
		mainJoystick.Y.whileHeld(new MoveBallUntilClick());
		*/
		mainJoystick.B.whileHeld(new DoubleShoot());
		mainJoystick.X.whenPressed(new ToggleShooterByRPM());
		mainJoystick.POV_UP.whenPressed(new ClimbMoveToPosition(ClimbState.PULL_UP));
		mainJoystick.POV_LEFT.whenPressed(new ClimbMoveToPosition(ClimbState.TRAVERSE));
		mainJoystick.POV_RIGHT.whenPressed(new ClimbMoveToPosition(ClimbState.MID_GAME));
		mainJoystick.POV_DOWN.whenPressed(new ClimbMoveToPosition(ClimbState.HANGAR));
		Climb.getInstance().setDefaultCommand(new ClimbByJoysticks(mainJoystick));
		mainJoystick.Y.whileHeld(new WhileHeldCoast());
		mainJoystick.START.whenPressed(new FullClimb(mainJoystick));

	
	private void initRealButtons() {
		//Intake.getInstance().initDefaultCommand(secondJoystick);
		//Shooter.getInstance().initDefaultCommand(secondJoystick);
		secondJoystick.A.whileHeld(new CalibratePID(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 3100) {

			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		secondJoystick.X.whileHeld(new InsertIntoShooter());
		
		secondJoystick.R1.whileHeld(new ParallelCommandGroup(new HandleBalls(), new RollByConstant(1.0)));
		
		secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());
//		secondJoystick.POV_LEFT.whenPressed(new InitManualOverride());
		
		
		Chassis.getInstance().initDefaultCommand(mainJoystick);
	}
	
	private class InitManualOverride extends GBCommand {
		
		private InitManualOverride() {
			super();
		}
		
		@Override
		public void initialize() {
			super.initialize();
			secondJoystick.R1.whileHeld(new RollByConstant(0.8));
			secondJoystick.L1.whileHeld(new RollByConstant(-0.8));
			
			secondJoystick.X.whileHeld(new RunFunnel());
			secondJoystick.B.whileHeld(new ReverseRunFunnel());
			
			secondJoystick.A.whileHeld(new ShootByConstant(0.6));
			secondJoystick.Y.whileHeld(new ShootByConstant(0.4));
			secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());
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
