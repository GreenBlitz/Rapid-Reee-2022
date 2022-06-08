package edu.greenblitz.pegasus.commands.chassis.driver;

import edu.greenblitz.gblib.gear.GearDependentValue;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class SmoothArcadeDrive extends ChassisCommand {

	private SmartJoystick joystick;
	private static final GearDependentValue forwardsFactor = new GearDependentValue(0.6, 0.6);
	private static final GearDependentValue turnFactor = new GearDependentValue(0.2 , 0.2);
	private static final GearDependentValue forwardsNonLinearityFactor = new GearDependentValue(2.0, 2.0);
	private static final GearDependentValue turnNonLinearityFactor = new GearDependentValue(2.0, 2.0);


	public SmoothArcadeDrive(SmartJoystick joystick) {
		this.joystick = joystick;
	}

	@Override
	public void initialize() {
		chassis.setIdleMode(AbstractMotor.IdleMode.Brake);
	}

	@Override
	public void execute() {
		double forward = Math.pow(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y), forwardsNonLinearityFactor.getValue()) * Math.signum(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y));
		double turn = Math.pow(joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X), turnNonLinearityFactor.getValue()) * Math.signum(joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X));
		chassis.arcadeDrive(forward * forwardsFactor.getValue(),
				turn*turnFactor.getValue());
	}
}
