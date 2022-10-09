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
import edu.greenblitz.pegasus.commands.shooter.ShootByRPM2;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class OI  {
	
	private enum IOModes {
		DEBUG, REAL, DEBUG2, AMIR
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
			case DEBUG2:
				initDebug2Buttons();
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
	}
	
	private void initDebug2Buttons() {
		mainJoystick.X.whenPressed(new CompressorOn());
		mainJoystick.A.whileHeld(new CompressorState());
		mainJoystick.B.whenPressed(new ToggleRoller());
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick,true));
	}
	
	private void initRealButtons() {
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick,false));

		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());
//		secondJoystick.A.whileHeld(new ShooterByRPM(1000));

		secondJoystick.A.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM){
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		secondJoystick.X.whileHeld(new InsertIntoShooter());

		secondJoystick.B.whileHeld(
				new ParallelCommandGroup(new MoveBallUntilClick(), new RollByConstant(1.0))
		);

		secondJoystick.START.whenPressed(new ToggleRoller());
		secondJoystick.BACK.whenPressed(new EjectEnemyBallFromShooter());
	}

	private void initAmirButtons(){
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick,true));

		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());

		secondJoystick.X.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM){
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		secondJoystick.A.whileHeld(new InsertIntoShooter());

		secondJoystick.B.whileHeld(
				new ParallelCommandGroup(new MoveBallUntilClick(), new RollByConstant(1.0))
		);

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
