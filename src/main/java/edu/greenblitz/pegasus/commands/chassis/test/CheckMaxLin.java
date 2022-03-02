package edu.greenblitz.pegasus.commands.chassis.test;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import org.greenblitz.debug.RemoteCSVTarget;

public class CheckMaxLin extends ChassisCommand {

    int count;
    private double power;
    private double previousVel;
    private double previousTime;
    private RemoteCSVTarget target;
    private long tStart;

    public CheckMaxLin(double power) {
        this.power = power;
    }

    @Override
    public void initialize() {
        previousTime = System.currentTimeMillis() / 1000.0;
        previousVel = 0;
        count = 0;
        tStart = System.currentTimeMillis();
        target = RemoteCSVTarget.initTarget("LinearData", "time", "vel", "acc");
    }

    @Override
    public void execute() {
        count++;

        Chassis.getInstance().moveMotors(power, power);

        double time = System.currentTimeMillis() / 1000.0;
        double V = Chassis.getInstance().getLinearVelocity();
        target.report(time - tStart / 1000.0, V, (V - previousVel) / (time - previousTime));
        previousTime = time;
        previousVel = V;

    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - tStart > 3000;
    }
}
