package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public class TurningByJoystick extends TurningCommand {
	private final SmartJoystick joystick;

	public TurningByJoystick(SmartJoystick joystick) {
		super();
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		super.execute();
		if (joystick.L1.get()) {
			if (joystick.R1.get()) {
				RobotContainer.getInstance().getClimb().resetTurningMotorRotations();
			}
			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.unsafeMoveTurningMotor(turningMotorPower * 0.2);
		} else {
			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.safeMoveTurningMotor(turningMotorPower * 0.2);
		}
	}
}
