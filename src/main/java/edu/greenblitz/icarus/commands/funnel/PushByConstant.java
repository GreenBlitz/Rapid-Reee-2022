package edu.greenblitz.icarus.commands.funnel;

public class PushByConstant extends FunnelCommand {

    private double power;

    public PushByConstant(double power) {
        super();
        this.power = power;
    }

    @Override
    public void execute() {
        funnel.moveMotor(power);
    }

    @Override
    public void end(boolean interrupted) {
        funnel.moveMotor(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
