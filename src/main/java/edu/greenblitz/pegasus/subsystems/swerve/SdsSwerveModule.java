package edu.greenblitz.pegasus.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

public class SdsSwerveModule implements SwerveModule{
	public static final double ANG_GEAR_RATIO = 1 / (150.0/7); //   input/output
	public static final double LIN_GEAR_RATIO = 1 / 8.0;

	public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI; //very accurate right now
	public static final double linTicksToMeters = LIN_GEAR_RATIO * RobotMap.Pegasus.motors.FALCON_TICKS_PER_RADIAN * WHEEL_CIRC;
	public static final double angleTicksToWheelToRPM = ANG_GEAR_RATIO * RobotMap.Pegasus.motors.FALCON_VELOCITY_UNITS_PER_RPM;
	public static final double linTicksToWheelToRPM = LIN_GEAR_RATIO * RobotMap.Pegasus.motors.FALCON_VELOCITY_UNITS_PER_RPM;
	public static final double angleTicksToRadians = ANG_GEAR_RATIO * RobotMap.Pegasus.motors.FALCON_TICKS_PER_RADIAN;


	public double targetAngle;
	public double targetVel;
	private TalonFX angleMotor;
	private TalonFX linearMotor;
	private AnalogInput magEncoder;
	private SimpleMotorFeedforward feedforward;

	public SdsSwerveModule(int angleMotorID, int linearMotorID, int lampreyID, boolean linInverted) {
		//SET ANGLE MOTO
		angleMotor = new TalonFX(angleMotorID);
		angleMotor.configSupplyCurrentLimit(
				new SupplyCurrentLimitConfiguration(
						true,30,30,0));
		angleMotor.configClosedloopRamp(0.4);
		angleMotor.setInverted(RobotMap.Pegasus.Swerve.angleMotorInverted);

		angleMotor.configSelectedFeedbackCoefficient(/*angleTicksToRadians*/1);

		linearMotor = new TalonFX(linearMotorID);
		linearMotor.configSupplyCurrentLimit(
				new SupplyCurrentLimitConfiguration(
						true,30,30,0));
		linearMotor.configClosedloopRamp(0.4);
		linearMotor.configOpenloopRamp(0.4);
		linearMotor.setInverted(linInverted);
		linearMotor.configSelectedFeedbackCoefficient(linTicksToMeters);

		magEncoder = new AnalogInput(lampreyID);
		magEncoder.setAverageBits(2); //todo find real bits
		configAnglePID(RobotMap.Pegasus.Swerve.angPID);
		configLinPID(RobotMap.Pegasus.Swerve.linPID);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);;
	}


	/**
	 * @param moduleState - @class {@link SwerveModuleState} to set the module to (angle and velocity)
	 */
	@Override
	public void setModuleState(SwerveModuleState moduleState) {
		setLinSpeed(moduleState.speedMetersPerSecond);
		rotateToAngle(moduleState.angle.getRadians());
	}

	/**
	 * @param angle - angle to turn to in radians
	 */
	@Override
	public void rotateToAngle(double angle) {
		double diff = GBMath.modulo(angle - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angle = getModuleAngle() + diff;

		angleMotor.set(ControlMode.Position,angle);

		targetAngle = angle;
	}

	/**
	 * @return get the module angle in radians
	 */
	@Override
	//maybe not after gear ratio plz check ~ noam
	public double getModuleAngle() {
		return angleMotor.getSelectedSensorPosition() ;
	}

	/**
	 * @return returns the current linear motor velocity
	 */
	@Override
	public double getCurrentVelocity() {
		return linearMotor.getSelectedSensorVelocity();
	}

	/**
	 * @param angle - Position to set for the angular encoder (in raw sensor units).
	 */
	@Override
	public void resetEncoderToValue(double angle) {
		angleMotor.setSelectedSensorPosition(angle);
	}

	/**
	 * sets the encoder current position to 0;
	 */
	@Override
	public void resetEncoderToValue() {
		angleMotor.setSelectedSensorPosition(0);
	}

	@Override
	public void configLinPID(PIDObject pidObject) {
		linearMotor.config_kP(0,pidObject.getKp());
		linearMotor.config_kI(0,pidObject.getKi());
		linearMotor.config_kD(0,pidObject.getKd());
		linearMotor.config_kF(0,pidObject.getKf());
		linearMotor.config_IntegralZone(0,pidObject.getIZone());
		linearMotor.configClosedLoopPeakOutput(0,pidObject.getMaxPower());
	}

	@Override
	public void configAnglePID(PIDObject pidObject) {
		angleMotor.config_kP(0,pidObject.getKp());
		angleMotor.config_kI(0,pidObject.getKi());
		angleMotor.config_kD(0,pidObject.getKd());
		angleMotor.config_kF(0,pidObject.getKf());
		angleMotor.config_IntegralZone(0,pidObject.getIZone());
		angleMotor.configClosedLoopPeakOutput(0,pidObject.getMaxPower());
	}

	/**
	 * @param speed - double of the wanted speed of the linear motor, uses PID and feedForward
	 */
	@Override
	public void setLinSpeed(double speed) {
		linearMotor.set(
				TalonFXControlMode.Velocity,
				speed,
				DemandType.ArbitraryFeedForward,
				feedforward.calculate(speed));
	}

	/**
	 * stops the angular and linear module.
	 */
	@Override
	public void stop() {
		linearMotor.set(ControlMode.PercentOutput,0);
		angleMotor.set(ControlMode.PercentOutput,0);
	}

	/**
	 * @return returns the target angle of the angular motor
	 */
	@Override
	public double getTargetAngle() {
		return targetAngle;
	}

	/**
	 * @return get the linear motor's target velocity
	 */
	@Override
	public double getTargetVel() {
		return targetVel;
	}

	/**
	 * @return return the @class {@link SwerveModuleState} object of the angle and velocity of the module
	 */
	@Override
	public SwerveModuleState getModuleState() {
		return new SwerveModuleState(
				getCurrentVelocity(),
				new Rotation2d(getModuleAngle())
				);
	}

	/**
	 * get the magEncoder's angle raw units (analog to digital converter)
	 * */
	@Override
	public double getAbsoluteEncoderValue() {
		return magEncoder.getValue();
	}

	/**
	 * @param power - double between 1 to -1 to set the percentage of the motors rotation
	 */
	@Override
	public void setRotPowerOnlyForCalibrations(double power) {
		angleMotor.set(ControlMode.PercentOutput, power);
	}
}
