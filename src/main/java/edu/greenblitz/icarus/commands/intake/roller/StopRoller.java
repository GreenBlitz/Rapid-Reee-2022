package edu.greenblitz.icarus.commands.intake.roller;

public class StopRoller extends RollerCommand{
    @Override
    public void initialize() {
        intake.moveRoller(0);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
