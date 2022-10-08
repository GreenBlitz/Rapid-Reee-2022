package edu.greenblitz.pegasus;


import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.MoveByJoystick;
import edu.greenblitz.pegasus.commands.swerve.RotateToAngle;
import edu.greenblitz.pegasus.commands.swerve.garbage.CalibrateMaxMin;

public class OI /*gevald */ {
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2
	}
	
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
	
	private void initDebugButtons() {
	}
	
	private void initDebug2Buttons() {
//		SwerveChassis.getInstance().setDefaultCommand(new MoveByJoystick(mainJoystick, 0.5));
//		mainJoystick.B.whileHeld(new CalibrateMaxMin(0.05, SwerveChassis.Module.FRONT_RIGHT));
//		mainJoystick.Y.whenPressed(new MoveByJoystick(mainJoystick, 0.3));
//		mainJoystick.A.whenPressed(new RotateToAngle(0));
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick));
//		mainJoystick.X.whenPressed(new CombineJoystickMovement(mainJoystick));
	
	}
	
	private void initRealButtons() {
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
