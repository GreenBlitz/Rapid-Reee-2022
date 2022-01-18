package edu.greenblitz.icarus.subsystems;

public class Chassis extends GBSubsystem {
	private static Chassis instance;
	
	public static void init() {
		if (instance == null) {
			instance = new Chassis();
//			instance.setDefaultCommand(
//					new ArcadeDrive(OI.getInstance().getMainJoystick())
//			);
		}
	}
	
	public static Chassis getInstance() {
		return instance;
	}
	
}
