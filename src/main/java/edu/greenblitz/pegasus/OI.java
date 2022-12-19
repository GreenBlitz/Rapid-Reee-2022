package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.RetractRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.shooter.FlipShooter;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.greenblitz.pegasus.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class OI {

	public enum IOModes {
		DEBUG,/* REAL,*/ AMIR
	}
	
	private static final IOModes IOMode = IOModes.AMIR; //decides which set of controls to init.
	private static OI instance;
	private static boolean isHandled = true;
	private final SmartJoystick mainJoystick;
	
	private final SmartJoystick secondJoystick;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.1);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		switch (IOMode) {
			case DEBUG:
				initDebugButtons();
				break;
//			case REAL:
//				initRealButtons();
//				break;
			case AMIR:
				initAmirButtons(); //todo do i really need to explain

		}
	}
	
	public static OI getInstance() {
		if (instance == null) {
			instance = new OI();
		}

		return instance;
	}
	
	private void initDebugButtons() {
//		SwerveChassis.getInstance().resetAllEncodersByValues();
//		SwerveChassis.getInstance().resetChassisAngle(0);
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, true));

		mainJoystick.Y.whenPressed(new SwerveCommand() {
			@Override
			public void initialize() {
				swerve.resetChassisAngle();
			}

			@Override
			public boolean isFinished() {
				return true;
			}
		});

		mainJoystick.POV_UP.whenPressed(new GBCommand() { //todo use instantCommand and dont have buttons disable proper control
			@Override
			public void initialize() {
				SwerveChassis.getInstance().resetAllEncoders();
			}

			@Override
			public boolean isFinished() {
				return true;
			}
		});
//		mainJoystick.X.whileHeld(new ShooterByRPM(2000));
//		mainJoystick.Y.whileHeld(new RunFunnel());
//		mainJoystick.A.whileHeld(new RunRoller());
//		mainJoystick.B.whenPressed(new ToggleRoller());
		
//		mainJoystick.L1.whenPressed(new ParallelCommandGroup(
//				new CalibrateMaxMin(0.2, SwerveChassis.Module.FRONT_RIGHT),
//				new CalibrateMaxMin(0.2, SwerveChassis.Module.FRONT_LEFT),
//				new CalibrateMaxMin(0.2, SwerveChassis.Module.BACK_RIGHT),
//				new CalibrateMaxMin(0.2, SwerveChassis.Module.BACK_LEFT)
//		));
		
		
	}
	
	private void initRealButtons() {
	}
	
	private void initAmirButtons() {
		//Indexing.getInstance().setDefaultCommand(new HandleBalls());
		SwerveChassis.getInstance().setDefaultCommand(new CombineJoystickMovement(mainJoystick, false ));
		mainJoystick.Y.whenPressed(new SwerveCommand() {
			@Override
			public void initialize() {
				swerve.resetChassisAngle();
				SmartDashboard.putNumber("pigeon",swerve.getChassisAngle());
			}

			@Override
			public boolean isFinished() {
				return true;
			}
		});

		mainJoystick.POV_UP.whenPressed(new GBCommand() { //todo use instantCommand and dont have buttons disable proper control
			@Override
			public void initialize() {
				SwerveChassis.getInstance().resetAllEncoders();
			}

			@Override
			public boolean isFinished() {

				return true;
			}
		});

		mainJoystick.R1.whileHeld(new GBCommand() { //todo make whenHeld possibly interruptible=false
			@Override
			public void initialize() {
				new RetractRoller().schedule();
			}

			@Override
			public void end(boolean interrupted) {
				new ExtendRoller().schedule();
			}
		});
		
		secondJoystick.Y.whileHeld(new EjectEnemyBallFromGripper());
		
		secondJoystick.R1.whileHeld(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM) { //todo replace with .andThan(new stopShooter())
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				shooter.setSpeedByPID(0);
			}
		});
		
		secondJoystick.L1.whenPressed(new FlipShooter()); //todo delete the whole-flipping-concept
		
		secondJoystick.A.whileHeld(new InsertIntoShooter());

	}

	public SmartJoystick getMainJoystick(){
		return mainJoystick;
	}
}
