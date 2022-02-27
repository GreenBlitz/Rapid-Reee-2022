package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.indexing.PrintColor;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;

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
		mainJoystick.A.whileHeld(new ParallelCommandGroup(
				new RunFunnel(),
				new ShootByConstant(0.8),
				new RollByConstant(0.8)
		));

		mainJoystick.POV_UP.whileHeld(new RollByConstant(0.8));
		mainJoystick.POV_RIGHT.whileHeld(new RunFunnel());
		mainJoystick.POV_DOWN.whileHeld(new ShootByConstant(0.8));
		mainJoystick.POV_LEFT.whileHeld(new ToggleRoller());

		Chassis.getInstance().initDefaultCommand(mainJoystick);
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
