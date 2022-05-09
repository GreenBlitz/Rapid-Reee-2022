package edu.greenblitz.pegasus.commands.chassis.driver;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class ArcadeDrive extends ChassisCommand {

    private SmartJoystick joystick;
    private static final GearDependentValue<Double> forwardsFactor = new GearDependentValue<>(1.0, 0.7);
    private static final GearDependentValue<Double> turnFactor = new GearDependentValue<>(0.2 , 0.2);

    public ArcadeDrive(SmartJoystick joystick) {
        this.joystick = joystick;
    }

    @Override
    public void initialize() {
        chassis.toBrake();
    }

    @Override
    public void execute() {
        chassis.arcadeDrive(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y)*forwardsFactor.getValue(),
                joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X)*turnFactor.getValue());
    }
}
