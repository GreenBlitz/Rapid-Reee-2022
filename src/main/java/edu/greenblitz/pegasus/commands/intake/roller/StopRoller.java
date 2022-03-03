package edu.greenblitz.pegasus.commands.intake.roller;

public class StopRoller extends RollerCommand{
    @Override
    public void initialize() {
        intake.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
