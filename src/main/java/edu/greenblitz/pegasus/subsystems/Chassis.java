package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.encoder.IEncoder;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.gblib.gyroscope.IGyroscope;
import edu.greenblitz.gblib.gyroscope.PigeonGyro;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.chassis.driver.ArcadeDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.Localizer;
import org.greenblitz.motion.base.Position;

public class Chassis extends GBSubsystem {
	private static Chassis instance;

	private final IEncoder leftEncoder, rightEncoder;
	private final IGyroscope gyroscope;
	private final CANSparkMax[] motors;

	private static final int[] ports = {
			RobotMap.Pegasus.Chassis.Motors.RIGHT_LEADER,
			RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_1,
			RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_2,
			RobotMap.Pegasus.Chassis.Motors.LEFT_LEADER,
			RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_1,
			RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_2
	};

	private static final boolean[] isReversed = {
			RobotMap.Pegasus.Chassis.Motors.RIGHT_LEADER_REVERSED,
			RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_1_REVERSED,
			RobotMap.Pegasus.Chassis.Motors.RIGHT_FOLLOWER_2_REVERSED,
			RobotMap.Pegasus.Chassis.Motors.LEFT_LEADER_REVERSED,
			RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_1_REVERSED,
			RobotMap.Pegasus.Chassis.Motors.LEFT_FOLLOWER_2_REVERSED
	};

	private Chassis() {
		motors = new CANSparkMax[ports.length];
		for(int i = 0; i < ports.length; i++){
			motors[i] = new CANSparkMax(ports[i], CANSparkMaxLowLevel.MotorType.kBrushless);
			motors[i].setSmartCurrentLimit(40);
			motors[i].setInverted(isReversed[i]);
		}

		motors[1].follow(motors[0]);
		motors[2].follow(motors[0]);
		motors[4].follow(motors[3]);
		motors[5].follow(motors[3]);

		leftEncoder = new SparkEncoder(RobotMap.Pegasus.Chassis.Encoders.NORM_CONST_SPARK, motors[3]);
		leftEncoder.invert(RobotMap.Pegasus.Chassis.Encoders.LEFT_ENCODER_REVERSED);
		rightEncoder = new SparkEncoder(RobotMap.Pegasus.Chassis.Encoders.NORM_CONST_SPARK, motors[0]);
		rightEncoder.invert(RobotMap.Pegasus.Chassis.Encoders.RIGHT_ENCODER_REVERSED);
		gyroscope = new PigeonGyro(new PigeonIMU(12)); //Pigeon connects to talon/CAN bus
		gyroscope.reset();
		gyroscope.inverse();
		toBrake();
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
		SmartDashboard.putNumber("Left Power", left);
		SmartDashboard.putNumber("Right Power", right);
		motors[0].set(right);
		motors[3].set(left);
	}

	public void moveMotors(double power){
		moveMotors(power, power);
	}

	public void toBrake() {
		for(CANSparkMax spark : motors){
			spark.setIdleMode(CANSparkMax.IdleMode.kBrake);
		}
	}

	public void toCoast() {
		for(CANSparkMax spark : motors){
			spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
		}
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

	public double getMeters(){
		return (getLeftMeters() + getRightMeters()) / 2.0;
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
		putNumber("Pigeon angle deg", Math.toDegrees(getAngle()));
		putString("Location", Chassis.getInstance().getLocation().toString());

	}

	public void resetEncoders() {
		rightEncoder.reset();
		leftEncoder.reset();
	}

	public void setMotorByID(int id, double power){
		motors[id].set(power);
	}




}
