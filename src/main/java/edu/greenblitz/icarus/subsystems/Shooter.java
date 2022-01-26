package edu.greenblitz.icarus.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.ControlType;
import edu.greenblitz.icarus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.interpolation.Dataset;
import org.greenblitz.motion.pid.PIDObject;

public class Shooter extends GBSubsystem {

	private static Shooter instance;

	// Leader is left, follower is right
	private CANSparkMax leader, follower;
	private Dataset rpmToPowerMap;
	private boolean preparedToShoot;

	private Shooter() {
		leader = new CANSparkMax(RobotMap.Icarus.Shooter.ShooterMotor.PORT_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
		follower = new CANSparkMax(RobotMap.Icarus.Shooter.ShooterMotor.PORT_FOLLOWER, CANSparkMaxLowLevel.MotorType.kBrushless);

		leader.setInverted(RobotMap.Icarus.Shooter.ShooterMotor.LEADER_INVERTED);
		follower.follow(leader, RobotMap.Icarus.Shooter.ShooterMotor.FOLLOWER_INVERTED);

		leader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		follower.setIdleMode(CANSparkMax.IdleMode.kCoast);

		leader.setSmartCurrentLimit(40);
		follower.setSmartCurrentLimit(40);

		preparedToShoot = false;
		putNumber("testing_target", 0);
		putNumber("p", 0);
		putNumber("i", 0);
		putNumber("d", 0);
		putNumber("f", 0);

//        leader.getEncoder().setVelocityConversionFactor(TICKS_PER_REVOLUTION);
//        encoder = new SparkEncoder(RobotMap.Limbo2.Shooter.NORMALIZER, leader);
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
		leader.getPIDController().setReference(target, ControlType.kVelocity);
	}

	public void setPIDConsts(PIDObject obj) {
		CANPIDController controller = leader.getPIDController();
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
		SmartDashboard.putNumber("Velocity", leader.getEncoder().getVelocity());
		putNumber("Output", leader.getAppliedOutput());
		putBoolean("ReadyToShoot", preparedToShoot);

	}

	public CANPIDController getPIDController() {
		return leader.getPIDController();
	}

	public void toCoast() {
		this.leader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		this.follower.setIdleMode(CANSparkMax.IdleMode.kCoast);
	}
}
