package edu.greenblitz.pegasus.commands.chassis;

import edu.greenblitz.pegasus.subsystems.Chassis;

public class BrakeChassis extends ChassisCommand{
    @Override
    public void initialize() {
        Chassis.getInstance().moveMotors(0, 0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
