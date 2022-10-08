package edu.greenblitz.pegasus;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.compressor.CompressorOn;
import edu.greenblitz.pegasus.commands.compressor.CompressorState;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;

public class OI  {
	
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
		mainJoystick.X.whenPressed(new CompressorOn());
		mainJoystick.A.whileHeld(new CompressorState());
		mainJoystick.B.whenPressed(new ToggleRoller());
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick));
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
