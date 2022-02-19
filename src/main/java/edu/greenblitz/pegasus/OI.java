package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.intake.RetractAndStop;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.Chassis;
import org.greenblitz.motion.pid.PIDObject;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;

	private boolean DEBUG = true;

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
	}

	private void initDebugButtons() {
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
