package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.chassis.BrakeChassis;
import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.intake.RetractAndStop;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Intake;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;

	private final boolean DEBUG = false;
	private static boolean isHandled = true;

	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		System.out.println("In OI");
		if(DEBUG) {
			initDebugButtons();
		}else{
			initRealButtons();
		}
	}

	private void initRealButtons() {
		Intake.getInstance().initDefaultCommand(secondJoystick);
		Shooter.getInstance().initDefaultCommand(secondJoystick); //change in the future

		secondJoystick.X.whenPressed(new ToggleRoller());
		secondJoystick.POV_LEFT.whenPressed(new InitManualOverride());

		Chassis.getInstance().initDefaultCommand(mainJoystick);
	}

	private void initDebugButtons() {
		Chassis.getInstance().initDefaultCommand(mainJoystick);
		mainJoystick.X.whileHeld(new ShootByConstant(0.8));
		mainJoystick.Y.whileHeld(new RollByConstant(1));
		mainJoystick.START.whenPressed(new BrakeChassis());
		//mainJoystick.START.whenPressed(new ToPower());
		//mainJoystick.BACK.whenPressed(new ToSpeed());
		mainJoystick.A.whileHeld(new ExtendAndCollect(0.3));
		mainJoystick.Y.whenPressed(new RetractAndStop());
		mainJoystick.B.whileHeld(new InsertByConstants(0.6));
		mainJoystick.R1.whenPressed(new ToggleRoller());
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

			secondJoystick.X.whileHeld(new InsertByConstants(0.8));
			secondJoystick.B.whileHeld(new InsertByConstants(-0.8));

			secondJoystick.A.whileHeld(new ShootByConstant(0.6));
			secondJoystick.Y.whileHeld(new ShootByConstant(0.4));
			secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());
		}

		@Override
		public boolean isFinished() {
			return true;
		}
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
