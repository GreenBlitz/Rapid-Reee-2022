package edu.greenblitz.pegasus;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.commands.DoUntilCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.handleBalls.HandleBalls;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.RetractRoller;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.shooter.FlipShooter;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.ShooterEvacuate;
import edu.greenblitz.pegasus.commands.swerve.CombineJoystickMovement;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.greenblitz.pegasus.commands.swerve.garbage.CalibrateMaxMin;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

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
				SwerveChassis.getInstance().resetAllEncodersByValues();
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
				SwerveChassis.getInstance().resetAllEncodersByValues();
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


		secondJoystick.B.whileHeld(new RunRoller());

		
		secondJoystick.START.whenPressed(new ToggleRoller()); //todo use .toggle
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
