package edu.greenblitz.pegasus;


import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.ShootByJoystick;
import edu.greenblitz.pegasus.commands.ShootByPower;
import edu.greenblitz.pegasus.commands.ToggleShooter;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.*;

public class OI {
	private static final IOModes IOMode = IOModes.REAL; //decides which set of controls to init.
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

	}

	private void initDebugButtons() {
	}

	private void initRealButtons() {
		mainJoystick.X.whileHeld(new ShootByPower(0.3));
		Shooter.getInstance().setDefaultCommand(new ShootByJoystick(mainJoystick));
		mainJoystick.A.whenPressed(new ToggleShooter());
		mainJoystick.B.whenPressed(new ShootByPower(1));
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
		}

		@Override
		public boolean isFinished() {
			return true;
		}
	}
}
