package edu.greenblitz.icarus.commands.intake.extender;

public class RetractRoller extends ExtenderCommand{
    @Override
    public void initialize() {
        intake.retract();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
