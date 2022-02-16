package edu.greenblitz.icarus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.icarus.commands.chassis.driver.ArcadeDrive;
import edu.greenblitz.icarus.commands.complexClimb.MoveHookMotorByConstant;
import edu.greenblitz.icarus.commands.funnel.PushByConstant;
import edu.greenblitz.icarus.commands.intake.ExtendAndCollect;
import edu.greenblitz.icarus.commands.shooter.ShootByConstant;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;

	private boolean DEBUG = true;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Icarus.Joystick.MAIN);
		secondJoystick = new SmartJoystick(RobotMap.Icarus.Joystick.SECOND);
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
		System.out.println("Init");
		//mainJoystick.A.whenPressed(new ShootByConstant(0.3));
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
