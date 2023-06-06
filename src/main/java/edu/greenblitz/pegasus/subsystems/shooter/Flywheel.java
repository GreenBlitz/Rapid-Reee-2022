package edu.greenblitz.pegasus.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.cords.Point;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;

public class Flywheel extends GBSubsystem {
    private GBSparkMax motor;
    private double speedTarget;
    private static Flywheel instance;

    private Flywheel() {
        this.motor = new GBSparkMax(RobotMap.Pegasus.Shooter.FlyWheel.ID, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.config(RobotMap.Pegasus.Shooter.FlyWheel.FLYWHEEL_CONF);
        speedTarget = 0;
    }

    public static Flywheel getInstance() {
        if(instance == null){
            instance = new Flywheel();
        }
        return instance;
    }

    public void setPower(double power) {
        this.motor.set(power);
    }

        public void setShooterSpeed(double target) {
        this.speedTarget = target;
        motor.getPIDController().setReference(speedTarget , CANSparkMax.ControlType.kVelocity, 0, RobotMap.Pegasus.Shooter.FlyWheel.FEED_FORWARD.calculate(speedTarget), SparkMaxPIDController.ArbFFUnits.kVoltage);
    }

    public double getShooterSpeed() {
        return motor.getEncoder().getVelocity();
    }

    public void resetEncoder() {
        motor.getEncoder().setPosition(0);
    }

    public boolean isPreparedToShoot() {
        return getShooterSpeed() - this.speedTarget < RobotMap.Pegasus.Shooter.FlyWheel.TOLERANCE;
    }


    @Override
    public void periodic() {
        Point target = RobotMap.GameField.SHOOTING_TARGET_POINT;

        double distance = Point.dist(target, new Point(
                SwerveChassis.getInstance().getRobotPose().getX(),
                SwerveChassis.getInstance().getRobotPose().getY()
        ));
        this.setShooterSpeed(RobotMap.Pegasus.Shooter.DISTANCE_TO_SHOOTING.linearlyInterpolate(distance)[1]);
    }
}
