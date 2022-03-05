package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.pegasus.RobotMap;

public class Climb extends GBSubsystem {
	private static Climb instance;

	// Rail motor moves the system forward and backward
	// Turning motor changes the angle of the system itself
	private CANSparkMax RailMotor, turningMotor;
	private SparkEncoder RailEncoder, turningEncoder;
	private boolean turningMode = false;

	private Climb() {
<<<<<<< HEAD
		motor = new CANSparkMax(RobotMap.Pegasus.Climb.Motor.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		motor.setInverted(RobotMap.Pegasus.Climb.Motor.MOTOR_REVERSE);
		encoder = new SparkEncoder(RobotMap.Pegasus.Climb.Motor.MOTOR_TICKS_PER_METER, motor);
=======
		RailMotor = new CANSparkMax(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		RailMotor.setInverted(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_REVERSED);
		RailEncoder = new SparkEncoder(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_TICKS_PER_METER, RailMotor);

		turningMotor = new CANSparkMax(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		turningMotor.setInverted(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_REVERSED);
		turningEncoder = new SparkEncoder(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_TICKS_PER_METER, turningMotor);
>>>>>>> c55f9c4 (Nitzan & Raz - started reworking climb, added basic functionality)
	}

	@Override
	public void periodic() {
		super.periodic();
		//Add data to smartdashboard here
	}

	private static void init() {
		instance = new Climb();
	}

	public static Climb getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public void moveRailMotor(double power) {
		RailMotor.set(power);
	}

	public void moveTurningMotor(double power) {
		turningMotor.set(power);
	}

	public double getRailMotorTicks() {
		return RailEncoder.getRawTicks();
	}

	public double getTurningMotorTicks() {
		return turningEncoder.getRawTicks();
	}
}
