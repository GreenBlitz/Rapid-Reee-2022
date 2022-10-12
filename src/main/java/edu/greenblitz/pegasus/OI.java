package edu.greenblitz.pegasus;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.commands.DoUntilCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.auto.ThreeBallAuto;
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
import edu.greenblitz.pegasus.commands.swerve.PathFollowerCommand;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.greenblitz.pegasus.commands.swerve.TragectoryCreator;
import edu.greenblitz.pegasus.commands.swerve.garbage.MoveLin;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.ArrayList;

public class OI {

	public enum IOModes {
		DEBUG, REAL, AMIR
	}

	private static final IOModes IOMode = IOModes.DEBUG; //decides which set of controls to init.
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
		SwerveChassis.getInstance().resetChassisAngle(0);
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, false));
		mainJoystick.X.whileHeld(new ShooterByRPM(2000));
		mainJoystick.Y.whileHeld(new RunFunnel());
		mainJoystick.A.whileHeld(new RunRoller());
		mainJoystick.B.whileHeld(new ToggleRoller());
	}

	private void initRealButtons() {
	}

	private void initAmirButtons() {
		Indexing.getInstance().setDefaultCommand(new HandleBalls());
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, false));
		mainJoystick.Y.whenPressed(new SwerveCommand() {
			@Override
			public void initialize() {
				swerve.resetChassisAngle(0);
			}
		});

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
		secondJoystick.POV_DOWN.whileHeld(new RunFunnel());
		secondJoystick.POV_UP.whenPressed(new ShooterEvacuate());
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
