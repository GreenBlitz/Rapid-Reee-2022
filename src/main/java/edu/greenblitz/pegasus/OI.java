package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.RotateAllWheelsToAngle;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.StartEndCommand;


public class OI { //GEVALD


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

	public static boolean isIsHandled() {
		return isHandled;
	}

	public static void disableHandling() {
		isHandled = false;
	}

	private void initButtons() {
		mainJoystick.A.whenHeld(new StartEndCommand(() -> SwerveChassis.getInstance().rotateModuleByPower(SwerveChassis.Module.BACK_RIGHT, 0.1),
				()->SwerveChassis.getInstance().rotateModuleByPower(SwerveChassis.Module.BACK_RIGHT, 0)));
		mainJoystick.B.whileHeld(new RotateAllWheelsToAngle(Math.PI/2));
		mainJoystick.X.whileHeld(new RotateAllWheelsToAngle(0));
		
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, true));
/*
		mainJoystick.Y.whenPressed(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisAngle()));

		mainJoystick.POV_UP.whenPressed(new InstantCommand(() -> SwerveChassis.getInstance().resetAllEncoders()));

		mainJoystick.R1.whenHeld(new StartEndCommand(() -> Intake.getInstance().getExtender().retract(),
				() -> Intake.getInstance().getExtender().extend()));

		secondJoystick.Y.whenHeld(new EjectEnemyBallFromGripper());

		secondJoystick.R1.whenHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM).andThen(new StopShooter()));

		secondJoystick.A.whenHeld(new InsertIntoShooter());

		secondJoystick.B.whenHeld(new RunRoller().alongWith(new RunFunnel().until(() -> Funnel.getInstance().isMacroSwitchPressed())));


		secondJoystick.START.toggleWhenPressed(new ToggleRoller());
		secondJoystick.POV_DOWN.whileHeld(new RunFunnel());
		secondJoystick.POV_UP.whenPressed(new ShooterEvacuate());*/

	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}

}