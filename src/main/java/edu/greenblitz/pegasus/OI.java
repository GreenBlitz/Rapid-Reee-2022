package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

import edu.greenblitz.pegasus.commands.ShootByPower;
import edu.greenblitz.pegasus.commands.ShootByTrigger;
import edu.greenblitz.pegasus.commands.ToggleShooter;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.*;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2
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
			case DEBUG2:
				initDebug2Buttons();
				break;
			
		}
	}
	
	private void initDebug2Buttons() {



	}
	private void initDebugButtons() {
		mainJoystick.A.whileHeld(new ShootByPower(0.1));
//		mainJoystick.B.whileHeld(new ShootByTrigger());
		Shooter.getInstance().initDefaultCommand();
		mainJoystick.X.whenPressed(new ToggleShooter());
	}
	private void initRealButtons() {
	}
	
	private class InitManualOverride extends GBCommand {
		
		private InitManualOverride() {
			super();
		}
		
		@Override
		public void initialize() {
			CommandScheduler.getInstance().cancelAll();
			super.initialize();
		}
		
		@Override
		public boolean isFinished() {
			return true;
		}
	}
	
	private void initTalButtons() {

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
