package edu.greenblitz.pegasus;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.commands.DoUntilCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.handleBalls.HandleBalls;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.FlipShooter;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.ShooterEvacuate;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.garbage.MoveLin;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.utils.DigitalInputMap;

public class OI {

	public enum IOModes {
		DEBUG, REAL, AMIR
	}

	private static final IOModes IOMode = IOModes.AMIR; //decides which set of controls to init.
	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;

	private final SmartJoystick secondJoystick;

	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		switch (IOMode) {
			case DEBUG:
				initDebugButtons();
				break;
			case REAL:
				initRealButtons();
				break;
			case AMIR:
				initAmirButtons();
		}
	}

	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}

	private void initDebugButtons() {
		secondJoystick.X.whenPressed(new DoubleShoot());
	}



	private void initRealButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, false));

		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());
//		secondJoystick.A.whileHeld(new ShooterByRPM(1000));

		secondJoystick.B.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM) {
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		//secondJoystick.X.whileHeld(new InsertIntoShooter());
		secondJoystick.X.whileHeld(new MoveLin(0, 0.2, new PIDObject(0.2)));
		secondJoystick.A.whileHeld(new MoveBallUntilClick());

		secondJoystick.START.whenPressed(new ToggleRoller());
	}

	private void initAmirButtons() {
		Indexing.getInstance().setDefaultCommand(new HandleBalls());
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, false));

		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());

		secondJoystick.R1.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM) {
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});

		secondJoystick.L1.whenPressed(new FlipShooter());

		secondJoystick.A.whileHeld(new InsertIntoShooter());


		secondJoystick.B.whileHeld(new RunRoller().alongWith(
				new DoUntilCommand(() -> DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.DigitalInputMap.MACRO_SWITCH), new RunFunnel())));
		//always activates roller, only activates funnel until macroSwitch;


		secondJoystick.START.whenPressed(new ToggleRoller());

		secondJoystick.BACK.whenPressed(new ShooterEvacuate());
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

	public IOModes getIOMode() {
		return IOMode;
	}
}
