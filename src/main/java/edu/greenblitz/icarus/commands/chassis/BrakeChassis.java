package edu.greenblitz.icarus.commands.chassis;

import edu.greenblitz.icarus.subsystems.Chassis;

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
