package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.SpinnerCommand;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;


public class OI {
	
	
	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;
	
	private final SmartJoystick secondJoystick;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.1);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		initButtons();
		
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	
	private void initButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, false));
		mainJoystick.A.whileHeld(new SpinnerCommand());
//
//		mainJoystick.Y.whenPressed(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisAngle()));
//
//		mainJoystick.POV_UP.whenPressed(new InstantCommand(() -> SwerveChassis.getInstance().resetAllEncoders()));
//
//		mainJoystick.R1.whenHeld(new StartEndCommand(() -> Intake.getInstance().getExtender().retract(),
//				() -> Intake.getInstance().getExtender().extend()));
//
//		secondJoystick.Y.whenHeld(new EjectEnemyBallFromGripper());
//
//		secondJoystick.R1.whenHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM).andThen(new StopShooter()));
//
//		secondJoystick.A.whenHeld(new InsertIntoShooter());
//
//		secondJoystick.B.whenHeld(new RunRoller().alongWith(new RunFunnel().until(() -> DigitalInputMap.getInstance().getValue(0))));
//
//
//		secondJoystick.START.toggleWhenPressed(new ToggleRoller());
//		secondJoystick.POV_DOWN.whileHeld(new RunFunnel());
//		secondJoystick.POV_UP.whenPressed(new ShooterEvacuate());
//
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
