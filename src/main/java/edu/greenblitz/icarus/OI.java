package edu.greenblitz.icarus;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Icarus.Joystick.MAIN);
		secondJoystick = new SmartJoystick(RobotMap.Icarus.Joystick.SECOND);
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
