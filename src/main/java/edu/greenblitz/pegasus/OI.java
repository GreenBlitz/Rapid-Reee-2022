package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.chassis.MoveMotorByID;
import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.intake.RetractAndStop;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.Chassis;
import org.greenblitz.motion.pid.PIDObject;

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
		//Chassis.getInstance().initDefaultCommand();
		mainJoystick.POV_DOWN.whileHeld(new MoveMotorByID(0, 0.3));
		mainJoystick.POV_LEFT.whileHeld(new MoveMotorByID(1, 0.3));
		mainJoystick.POV_UP.whileHeld(new MoveMotorByID(2, 0.3));
		mainJoystick.POV_RIGHT.whileHeld(new MoveMotorByID(3, 0.3));
		mainJoystick.R1.whileHeld(new MoveMotorByID(4, 0.3));
		mainJoystick.L1.whileHeld(new MoveMotorByID(5, 0.3));
		//mainJoystick.START.whenPressed(new ToPower());
		//mainJoystick.BACK.whenPressed(new ToSpeed());
		//mainJoystick.A.whileHeld(new ExtendAndCollect(0.3));
		//mainJoystick.Y.whenPressed(new RetractAndStop());
		//mainJoystick.B.whileHeld(new InsertByConstants(0.2));
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
