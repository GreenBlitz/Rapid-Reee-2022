package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;

public class RailByJoystick extends RailCommand {

	private final SmartJoystick joystick;
	double powerFactor;

	public RailByJoystick(SmartJoystick joystick, double powerFactor) {
		super();
		this.joystick = joystick;
		this.powerFactor = powerFactor;
	}

	public RailByJoystick(SmartJoystick joystick) {
		this(joystick, 1);
	}

	@Override
	public void execute() {
		super.execute();
		if (joystick.L1.get()) {
			if (joystick.R1.get()) {
				Climb.getInstance().resetRailMotorTicks();
			}
			double railMotorPower = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * powerFactor;
			climb.unsafeMoveRailMotor(railMotorPower);
		} else {
			double railMotorPower = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y) * powerFactor;
			climb.safeMoveRailMotor(railMotorPower);

		}
	}
}
