package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.*;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.interpolation.Dataset;
import org.greenblitz.motion.pid.PIDObject;

public class Shooter extends GBSubsystem {

	private static Shooter instance;

	// Leader is left, follower is right
	private CANSparkMax leader; //, follower;
	private Dataset rpmToPowerMap;
	private boolean preparedToShoot;

	private Shooter() {
		leader = new CANSparkMax(RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
//		follower = new CANSparkMax(RobotMap.Pegasus.Shooter.ShooterMotor.PORT_FOLLOWER, CANSparkMaxLowLevel.MotorType.kBrushless);

		leader.setInverted(RobotMap.Pegasus.Shooter.ShooterMotor.LEADER_INVERTED);
//		follower.follow(leader, RobotMap.Pegasus.Shooter.ShooterMotor.FOLLOWER_INVERTED);

		leader.setIdleMode(CANSparkMax.IdleMode.kCoast);
//		follower.setIdleMode(CANSparkMax.IdleMode.kCoast);

		leader.setSmartCurrentLimit(40);
//		follower.setSmartCurrentLimit(40);

		preparedToShoot = false;
		putNumber("testing_target", 0);
		putNumber("p", 0);
		putNumber("i", 0);
		putNumber("d", 0);
		putNumber("f", 0);

	}

	public static void init() {
		instance = new Shooter();
	}

	public static Shooter getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public void shoot(double power) {
		putNumber("power", power);
		if (power == 0) {
			this.toCoast();
		}
		this.leader.set(power);
	}

	public double getDesiredPower(double rpm) {
		return rpmToPowerMap.linearlyInterpolate(rpm)[0];
	}

	public void setSpeedByPID(double target) {
		leader.getPIDController().setReference(target, CANSparkMax.ControlType.kVelocity);
	}

	public void setPIDConsts(PIDObject obj) {
		SparkMaxPIDController controller = leader.getPIDController();
		controller.setP(obj.getKp());
		controller.setI(obj.getKi());
		controller.setD(obj.getKd());
		controller.setFF(obj.getKf());
	}


	public double getShooterSpeed() {
		return leader.getEncoder().getVelocity();
	}

	public double getAbsoluteShooterSpeed() {
		return Math.abs(getShooterSpeed());
	}

	public void resetEncoder() {
		leader.getEncoder().setPosition(0);
	}

	public boolean isPreparedToShoot() {
		return preparedToShoot;
	}

	public void setPreparedToShoot(boolean preparedToShoot) {
		this.preparedToShoot = preparedToShoot;
	}

	@Override
	public void periodic() {

		putNumber("Position", leader.getEncoder().getPosition());
		putNumber("Velocity", leader.getEncoder().getVelocity());
		putNumber("Output", leader.getAppliedOutput());
		putBoolean("ReadyToShoot", preparedToShoot);

	}

	public SparkMaxPIDController getPIDController() {
		return leader.getPIDController();
	}

	public void toCoast() {
		this.leader.setIdleMode(CANSparkMax.IdleMode.kCoast);
//		this.follower.setIdleMode(CANSparkMax.IdleMode.kCoast);
	}
}
