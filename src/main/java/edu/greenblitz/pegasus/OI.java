package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Intake;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

public class OI { //GEVALD

	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;
	
	private final SmartJoystick thirdJoyStick;
	private final SmartJoystick secondJoystick;


	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.1);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		thirdJoyStick = new SmartJoystick(RobotMap.Pegasus.Joystick.debug);
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
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(false));

		mainJoystick.Y.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetChassisAngle(0)));
		mainJoystick.POV_UP.onTrue(new InstantCommand(() -> SwerveChassis.getInstance().resetAllEncoders()));

		mainJoystick.R1.whileTrue(new StartEndCommand(() -> Intake.getInstance().getExtender().retract(),
				() -> Intake.getInstance().getExtender().extend()));

		secondJoystick.Y.whileTrue(new EjectEnemyBallFromGripper());

		secondJoystick.R1.whileTrue(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM).andThen(new StopShooter()));


		secondJoystick.B.whileTrue(new RunRoller().alongWith(new RunFunnel().until(() -> Funnel.getInstance().isMacroSwitchPressed())));


		secondJoystick.START.onTrue(new ToggleRoller());
		secondJoystick.POV_DOWN.whileTrue(new RunFunnel());
		
		

		
		//debug joysticks

		
	}

	public SmartJoystick getMainJoystick() {
		return mainJoystick;
	}

	public SmartJoystick getSecondJoystick() {
		return secondJoystick;
	}

}