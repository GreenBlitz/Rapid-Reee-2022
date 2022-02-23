package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import edu.greenblitz.pegasus.commands.complexClimb.MoveHookMotorByConstant;
import edu.greenblitz.pegasus.commands.funnel.PushByConstant;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;

	private boolean DEBUG = true;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND);
		System.out.println("In OI");
		if(DEBUG) {
			initDebugButtons();
		}else{
			initRealButtons();
		}
	}

	private void initRealButtons() {
	}

	private void initDebugButtons() {
		mainJoystick.B.whenPressed(new ExtendAndCollect(0.3));
		mainJoystick.X.whenPressed(new PushByConstant(0.1));
		mainJoystick.Y.whenPressed(new MoveHookMotorByConstant(0.1));

		mainJoystick.A.whenHeld(new ArcadeDrive(mainJoystick));
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
}
