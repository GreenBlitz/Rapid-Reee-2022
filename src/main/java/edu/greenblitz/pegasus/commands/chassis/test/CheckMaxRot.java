package edu.greenblitz.pegasus.commands.chassis.test;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.debug.RemoteCSVTargetBuffer;

public class CheckMaxRot extends ChassisCommand {

    int count;
    private double power;
    private double previousAngle;
    private double previousVel;
    private double previousTime;
    private long tStart;
    private RemoteCSVTargetBuffer target;

    public CheckMaxRot(double power) {
        this.power = power;
    }

    @Override
    public void initialize() {
        previousTime = System.currentTimeMillis() / 1000.0;
        previousAngle = Chassis.getInstance().getLocation().getAngle();
        previousVel = 0;
        count = 0;
        tStart = System.currentTimeMillis();
        target = new RemoteCSVTargetBuffer("RotationalData", "time", "vel", "acc");
    }

    @Override
    public void execute() {
        count++;
        Chassis.getInstance().moveMotors(-power, power);
        double time = System.currentTimeMillis() / 1000.0;
        double angle = Chassis.getInstance().getLocation().getAngle();
        double V = (angle - previousAngle) / (time - previousTime);
        target.report(time - tStart / 1000.0, V, (V - previousVel) / (time - previousTime));
        previousAngle = angle;
        previousTime = time;
        previousVel = V;


        }

    @Override
    public void end(boolean inter) {
        System.out.println(System.currentTimeMillis() - tStart);
        chassis.moveMotors(0,0);
        target.passToCSV(true);
    }

    @Override
    public boolean isFinished() {
        return System.currentTimeMillis() - tStart > 5000;
    }
}
