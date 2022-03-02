package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;
	
	private boolean DEBUG = true;
	
	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		System.out.println("In OI");
		if (DEBUG) {
			initDebugButtons();
		} else {
			initRealButtons();
		}
	}
	
	private void initRealButtons() {
	}
	
	private void initDebugButtons() {
		mainJoystick.B.whenPressed(new ParallelRaceGroup(new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 2750), new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot())){
			@Override
			public void end(boolean interrupted) {
				super.end(interrupted);
				Shooter.getInstance().setSpeedByPID(0);
			}
		});
		mainJoystick.A.whileHeld(new ShootByConstant(0.5));
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
