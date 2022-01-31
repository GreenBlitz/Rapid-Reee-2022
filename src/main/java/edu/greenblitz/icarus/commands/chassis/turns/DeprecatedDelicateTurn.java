package edu.greenblitz.icarus.commands.chassis.turns;

/*
this was deprecated due to criminal non use of PID. replacement was not yet written so if you require use of this class;
you are encouraged to write it yourself;
 */
import edu.greenblitz.gblib.threading.IThreadable;
import edu.greenblitz.icarus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.base.Position;

public class DeprecatedDelicateTurn implements IThreadable {

    private double POWER = 0.2;
    private double TOL = Math.toRadians(1);
    private double goal;
    private double rightMult;

    public DeprecatedDelicateTurn(double goal) {
        this.goal = goal;
    }


    @Override
    public void run() {
        Chassis.getInstance().moveMotors(POWER * -rightMult, POWER * rightMult);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Position.normalizeAngle(Chassis.getInstance().getAngle() - goal)) <= TOL;
    }

    @Override
    public void atEnd() {
        double err = Math.toDegrees(Position.normalizeAngle(Chassis.getInstance().getAngle() - goal));
        SmartDashboard.putNumber("Final Final Error", err);

        Chassis.getInstance().moveMotors(0, 0);
    }

    @Override
    public void atInit() {
        rightMult = -Math.signum(Position.normalizeAngle(goal - Chassis.getInstance().getLocation().getAngle()));
        Chassis.getInstance().toBrake();
    }
}
