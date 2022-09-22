package edu.greenblitz.pegasus;


import edu.greenblitz.gblib.hid.SmartJoystick;
//import edu.greenblitz.pegasus.commands.swerve.MoveLinModule;
//import edu.greenblitz.pegasus.commands.swerve.MoveModuleLinAndAng;
//import edu.greenblitz.pegasus.commands.swerve.RotateToAngle;
//import edu.greenblitz.pegasus.commands.swerve.SetRotPower;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.swerve.MoveByJoystick;
import edu.greenblitz.pegasus.commands.swerve.garbage.CalibrateMaxMin;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

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
//				initRealButtons();
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
		SwerveChassis.getInstance().setDefaultCommand(new MoveByJoystick(mainJoystick, 1));
	}

	private void initDebugButtons() {
	}

	private void initRealButtons() {
	}

	private void initTalButtons() {
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

}
