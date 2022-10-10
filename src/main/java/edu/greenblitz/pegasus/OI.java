package edu.greenblitz.pegasus;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.compressor.CompressorOn;
import edu.greenblitz.pegasus.commands.compressor.CompressorState;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class OI  {
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2
	}
	
	private static final IOModes IOMode = IOModes.DEBUG2; //decides which set of controls to init.
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
			case DEBUG2:
				initDebug2Buttons();
				break;
		}
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}
		return instance;
	}
	
	private void initDebugButtons() {
	}
	
	private void initDebug2Buttons() {
		mainJoystick.X.whenPressed(new CompressorOn());
		mainJoystick.A.whileHeld(new CompressorState());
		mainJoystick.B.whenPressed(new ToggleRoller());
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick));
	}
	
	private void initRealButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick));

		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());

		secondJoystick.A.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM){
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		secondJoystick.X.whileHeld(new InsertIntoShooter());

		secondJoystick.B.whileHeld(new MoveBallUntilClick());

		secondJoystick.START.whenPressed(new ToggleRoller());
		secondJoystick.BACK.whenPressed(new EjectEnemyBallFromShooter());
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
