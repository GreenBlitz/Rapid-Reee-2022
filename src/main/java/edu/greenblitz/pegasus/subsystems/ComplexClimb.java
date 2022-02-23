package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.pegasus.RobotMap;

public class ComplexClimb extends GBSubsystem {
	private static ComplexClimb instance;

	// Hook motor moves the system forward and backward
	// Turning motor changes the angle of the system itself
	private CANSparkMax hookMotor, turningMotor;
	private SparkEncoder hookEncoder, turningEncoder;
	private boolean turningMode = false;

	private ComplexClimb() {
		hookMotor = new CANSparkMax(RobotMap.Pegasus.ComplexClimb.ComplexClimbMotor.HOOK_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		hookMotor.setInverted(RobotMap.Pegasus.ComplexClimb.ComplexClimbMotor.HOOK_MOTOR_REVERSED);
		hookEncoder = new SparkEncoder(RobotMap.Pegasus.ComplexClimb.ComplexClimbMotor.HOOK_MOTOR_TICKS_PER_METER, hookMotor);
		turningMotor = new CANSparkMax(RobotMap.Pegasus.ComplexClimb.ComplexClimbMotor.TURNING_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		turningMotor.setInverted(RobotMap.Pegasus.ComplexClimb.ComplexClimbMotor.TURNING_MOTOR_REVERSED);
		turningEncoder = new SparkEncoder(RobotMap.Pegasus.ComplexClimb.ComplexClimbMotor.TURNING_MOTOR_TICKS_PER_METER, turningMotor);
	}

	@Override
	public void periodic() {
		super.periodic();
		//Add data to smartdashboard here
	}

	private static void init() {
		instance = new ComplexClimb();
	}

	public static ComplexClimb getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	private void moveHookMotor(double power) {
		hookMotor.set(power);
	}

	public void safeMoveHookMotor(double power) {
		if (power < 0) {
			moveHookMotor(0);
			return;
		} else if (power > 1) {
			moveHookMotor(1);
			return;
		}
		moveHookMotor(power);
	}

	private void moveTurningMotor(double power) {
		turningMotor.set(power);
	}

	public void safeMoveTurningMotor(double power) {
		if (power < 0) {
			moveTurningMotor(0);
			return;
		} else if (power > 1) {
			moveTurningMotor(1);
			return;
		}
		moveTurningMotor(power);
	}

	public double getHookMotorTicks() {
		return hookEncoder.getRawTicks();
	}

	public double getTurningMotorTicks() {
		return turningEncoder.getRawTicks();
	}

	public void moveCurrentMotor(double power) {
		if (turningMode) safeMoveTurningMotor(power);
		else safeMoveHookMotor(power);
	}

	public double getCurrentEncoderTicks() {
		if (turningMode) return getTurningMotorTicks();
		else return getHookMotorTicks();
	}
}
