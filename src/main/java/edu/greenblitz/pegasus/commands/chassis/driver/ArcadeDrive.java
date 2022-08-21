package edu.greenblitz.pegasus.commands.chassis.driver;

import edu.greenblitz.gblib.gear.GearDependentValue;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class ArcadeDrive extends ChassisCommand {

    private SmartJoystick joystick;
    private static final GearDependentValue forwardsFactor = new GearDependentValue(1.0, 0.3);
    private static final GearDependentValue turnFactor = new GearDependentValue(0.2 , 0.2);

    public ArcadeDrive(SmartJoystick joystick) {
        this.joystick = joystick;
    }

    @Override
    public void initialize() {
        chassis.setIdleMode(AbstractMotor.IdleMode.Brake);
    }

    @Override
    public void execute() {
        chassis.arcadeDrive(joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y)*forwardsFactor.getValue(),
                joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X)*turnFactor.getValue());
    }
}
