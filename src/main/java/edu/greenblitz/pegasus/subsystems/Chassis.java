package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.encoder.IEncoder;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.gblib.gyroscope.IGyroscope;
import edu.greenblitz.gblib.gyroscope.PigeonGyro;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import org.greenblitz.motion.Localizer;
import org.greenblitz.motion.base.Position;

public class Chassis extends GBSubsystem {
	private static Chassis instance;

	private final CANSparkMax rightLeader, rightFollower1, rightFollower2, leftLeader, leftFollower1, leftFollower2;
	private final IEncoder leftEncoder, rightEncoder;
	private final IGyroscope gyroscope;
	private final CANSparkMax[] allMotors;

	private Chassis() {
		rightLeader = new CANSparkMax(RobotMap.Pegasus.Chassis.Motors.RIGHT_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
		rightFollower1 = new CANSparkMax(RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_1, CANSparkMaxLowLevel.MotorType.kBrushless);
		rightFollower2 = new CANSparkMax(RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_2, CANSparkMaxLowLevel.MotorType.kBrushless);
		leftLeader = new CANSparkMax(RobotMap.Pegasus.Chassis.Motors.LEFT_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
		leftFollower1 = new CANSparkMax(RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_1, CANSparkMaxLowLevel.MotorType.kBrushless);
		leftFollower2 = new CANSparkMax(RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_2, CANSparkMaxLowLevel.MotorType.kBrushless);   //big-haim
		allMotors = new CANSparkMax[] {rightLeader, rightFollower1, rightFollower2, leftLeader, leftFollower1, leftFollower2};

		for (CANSparkMax spark : allMotors){
			spark.setSmartCurrentLimit(40);
		}

		leftFollower1.follow(leftLeader);
		leftFollower2.follow(leftLeader);
		rightFollower1.follow(rightLeader);
		rightFollower2.follow(rightLeader);

		rightLeader.setInverted(RobotMap.Pegasus.Chassis.Motors.RIGHT_LEADER_REVERSED);
		rightFollower1.setInverted(RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_1_REVERSED);
		rightFollower2.setInverted(RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_2_REVERSED);
		leftLeader.setInverted(RobotMap.Pegasus.Chassis.Motors.LEFT_LEADER_REVERSED);
		leftFollower1.setInverted(RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_1_REVERSED);
		leftFollower2.setInverted(RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_2_REVERSED);

		leftEncoder = new SparkEncoder(RobotMap.Pegasus.Chassis.Encoders.NORM_CONST_SPARK, leftLeader);
		leftEncoder.invert(RobotMap.Pegasus.Chassis.Encoders.LEFT_ENCODER_REVERSED);
		rightEncoder = new SparkEncoder(RobotMap.Pegasus.Chassis.Encoders.NORM_CONST_SPARK, rightLeader);
		rightEncoder.invert(RobotMap.Pegasus.Chassis.Encoders.RIGHT_ENCODER_REVERSED);
		gyroscope = new PigeonGyro(new PigeonIMU(12)); //Pigeon connects to talon/CAN bus
		gyroscope.reset();
		gyroscope.inverse();
	}

	public static Chassis getInstance() {
		if (instance == null) {
			instance = new Chassis();
		}
		return instance;
	}

	public void changeGear() {
		leftEncoder.switchGear();
		rightEncoder.switchGear();
	}

	public void moveMotors(double left, double right) {
		putNumber("Left Power", left);
		putNumber("Right Power", right);
		rightLeader.set(right);
		leftLeader.set(left);
	}

	public void toBrake() {
		rightLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightFollower1.setIdleMode(CANSparkMax.IdleMode.kBrake);
		rightFollower2.setIdleMode(CANSparkMax.IdleMode.kBrake);
		leftLeader.setIdleMode(CANSparkMax.IdleMode.kBrake);
		leftFollower1.setIdleMode(CANSparkMax.IdleMode.kBrake);
		leftFollower2.setIdleMode(CANSparkMax.IdleMode.kBrake);
	}

	public void toCoast() {
		rightLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		rightFollower1.setIdleMode(CANSparkMax.IdleMode.kCoast);
		rightFollower2.setIdleMode(CANSparkMax.IdleMode.kCoast);
		leftLeader.setIdleMode(CANSparkMax.IdleMode.kCoast);
		leftFollower1.setIdleMode(CANSparkMax.IdleMode.kCoast);
		leftFollower2.setIdleMode(CANSparkMax.IdleMode.kCoast);
	}

	public void arcadeDrive(double moveValue, double rotateValue) {
		moveMotors(moveValue - rotateValue, moveValue + rotateValue);
	}

	public double getLeftMeters() {
		return leftEncoder.getNormalizedTicks();
	}

	public double getRightMeters() {
		return rightEncoder.getNormalizedTicks();
	}

	public double getLeftRate() {
		return leftEncoder.getNormalizedVelocity();
	}

	public double getRightRate() {
		return rightEncoder.getNormalizedVelocity();
	}

	public double getLinearVelocity() {
		return 0.5 * (getRightRate() + getLeftRate());
	}

	public double getAngularVelocityByWheels() {
		return (getRightRate() - getLeftRate())/getWheelDistance();
		//this is true, if u don't understand, go to your mommy and cry (ask asaf);
	}

	public double getAngle() {
		return gyroscope.getNormalizedYaw();
	}

	public double getRawAngle() {
		return gyroscope.getRawYaw();
	}

	public double getAngularVelocityByGyro() {
		return gyroscope.getYawRate();
	}

	public void resetGyro() {
		gyroscope.reset();
	}

	public double getWheelDistance() {
		return RobotMap.Pegasus.Chassis.WHEEL_DIST;
	}

	public Position getLocation() {
		return Localizer.getInstance().getLocation();
	}

	public void initDefaultCommand(SmartJoystick joystick){
		instance.setDefaultCommand(new ArcadeDrive(joystick));
	}
	
	@Override
	public void periodic() {
		super.periodic();
		putNumber("Left vel enc", leftEncoder.getNormalizedVelocity());
		putNumber("Right vel enc", rightEncoder.getNormalizedVelocity());
		putNumber("Angle vel by wheel", getAngularVelocityByWheels());
		putNumber("Pigeon angle deg", Math.toDegrees(getAngle()));
		putString("Location", Chassis.getInstance().getLocation().toString());
	}

	public void resetEncoders() {
		rightEncoder.reset();
		leftEncoder.reset();
	}

	public void setMotorByID(int id, double power){
		allMotors[id].set(power);
	}




}
