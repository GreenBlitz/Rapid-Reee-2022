package edu.greenblitz.pegasus.commands.chassis.driver;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.gblib.gears.GearDependentValue;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class SmoothArcadeDrive extends ChassisCommand {

	private SmartJoystick joystick;
	private static final GearDependentValue<Double> forwardsFactor = new GearDependentValue<>(0.6, 0.6);
	private static final GearDependentValue<Double> turnFactor = new GearDependentValue<>(0.2 , 0.2);
	private static final GearDependentValue<Double> forwardsNonLinearityFactor = new GearDependentValue<>(2.0, 2.0);
	private static final GearDependentValue<Double> turnNonLinearityFactor = new GearDependentValue<>(2.0, 2.0);


	public SmoothArcadeDrive(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void initialize() {
		chassis.toBrake();
	}

	@Override
	public void execute() {
		double forward = Math.pow(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y), forwardsNonLinearityFactor.getValue());
		double turn = Math.pow(joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X), turnNonLinearityFactor.getValue());
		chassis.arcadeDrive(forward * forwardsFactor.getValue(),
				turn*turnFactor.getValue());
	}
}
