package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByPID;
import edu.greenblitz.pegasus.commands.shooterCommands.ShootByPower;
import edu.greenblitz.pegasus.commands.shooterCommands.ToggleIsOn;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class OI {
	private static final IOModes IOMode = IOModes.DEBUG; //decides which set of controls to init.
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
		Shooter.getInstance().initDefaultCommand();
		mainJoystick.X.whileHeld(new ShootByPower(0.3));
		mainJoystick.A.whenPressed(new ToggleIsOn());
		mainJoystick.Y.whileHeld(new ShootByPID(1000));

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
