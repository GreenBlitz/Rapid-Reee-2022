package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.chassis.BrakeChassis;
import edu.greenblitz.pegasus.commands.chassis.MoveMotorByID;
import edu.greenblitz.pegasus.commands.colorSensor.PrintColor;
import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.intake.RetractAndStop;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class OI {
	private static OI instance;
	
	private SmartJoystick mainJoystick;
	private SmartJoystick secondJoystick;

	private boolean DEBUG = false;

	private OI() {
		mainJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.MAIN, 0.2);
		secondJoystick = new SmartJoystick(RobotMap.Pegasus.Joystick.SECOND, 0.2);
		System.out.println("In OI");
		if(DEBUG) {
			initDebugButtons();
		}else{
			initRealButtons();
		}
	}

	private void initRealButtons() {
		secondJoystick.R1.whileHeld(new RollByConstant(0.8));
		secondJoystick.L1.whileHeld(new RollByConstant(-0.8));
		//change these to use the triggers instead of the buttons please
		secondJoystick.X.whileHeld(new InsertByConstants(0.8));
		secondJoystick.B.whileHeld(new InsertByConstants(-0.8));

		secondJoystick.A.whileHeld(new ShootByConstant(0.7));
		secondJoystick.Y.whileHeld(new ShootByConstant(0.4));
		secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());

		Chassis.getInstance().initDefaultCommand(mainJoystick);

		secondJoystick.POV_UP.whenPressed(new PrintColor());
	}

	private void initDebugButtons() {
		secondJoystick.X.whenPressed(new SequentialCommandGroup(
				new ParallelRaceGroup(
					new WaitCommand(1),
					new ShootByConstant(0.7)
				),
				new ParallelRaceGroup(
						new WaitCommand(4),
						new ShootByConstant(0.7),
						new InsertByConstants(0.8)
				)
		));

		secondJoystick.B.whenPressed(new SequentialCommandGroup(
				new ParallelRaceGroup(
						new WaitCommand(1),
						new ShootByConstant(0.4)
				),
				new ParallelRaceGroup(
						new WaitCommand(4),
						new ShootByConstant(0.4),
						new InsertByConstants(0.8)
				)
		));

		secondJoystick.R1.whileHeld(new RollByConstant(0.8));
		secondJoystick.L1.whileHeld(new RollByConstant(-0.8));

		secondJoystick.POV_DOWN.whenPressed(new ToggleRoller());

		Chassis.getInstance().initDefaultCommand(mainJoystick);

		secondJoystick.POV_UP.whenPressed(new PrintColor());
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
