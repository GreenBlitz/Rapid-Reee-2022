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
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SdsSwerveModule implements SwerveModule{
	public static final double ANG_GEAR_RATIO = (150.0/7); //   input/output
	public static final double LIN_GEAR_RATIO = 8.14;

	public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI; //very accurate right now
	public static final double linTicksToMeters = RobotMap.Pegasus.motors.FALCON_TICKS_PER_RADIAN * WHEEL_CIRC / LIN_GEAR_RATIO;
	public static final double angleTicksToWheelToRPM = RobotMap.Pegasus.motors.FALCON_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
	public static final double linTicksToMetersPerSecond = RobotMap.Pegasus.motors.FALCON_VELOCITY_UNITS_PER_RPM / LIN_GEAR_RATIO * WHEEL_CIRC / 60;
	public static final double angleTicksToRadians = RobotMap.Pegasus.motors.FALCON_TICKS_PER_RADIAN / ANG_GEAR_RATIO;


	public double targetAngle;
	public double targetVel;
	private TalonFX angleMotor;
	private TalonFX linearMotor;
	private DutyCycleEncoder magEncoder;
	private SimpleMotorFeedforward feedforward;

	public SdsSwerveModule(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted) {
		//SET ANGLE MOTO
		angleMotor = new TalonFX(angleMotorID);
		angleMotor.configSupplyCurrentLimit(
				new SupplyCurrentLimitConfiguration(
						true,30,30,0));
		angleMotor.configClosedloopRamp(0.4);
		angleMotor.setInverted(RobotMap.Pegasus.Swerve.angleMotorInverted);


		linearMotor = new TalonFX(linearMotorID);
		linearMotor.configFactoryDefault();
		linearMotor.configSupplyCurrentLimit(
				new SupplyCurrentLimitConfiguration(
						true,30,30,0));
		linearMotor.configClosedloopRamp(0.4);
		linearMotor.configOpenloopRamp(0.4);
		linearMotor.setInverted(linInverted);

		magEncoder = new DutyCycleEncoder(AbsoluteEncoderID);
		configAnglePID(RobotMap.Pegasus.Swerve.angPID);
		configLinPID(RobotMap.Pegasus.Swerve.linPID);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);;
	}
	
	public static double convertRadsToTicks(double angInRads){
		return angInRads/angleTicksToRadians;
	}
	public static double convertTicksToRads(double angInTicks){
		return angInTicks*angleTicksToRadians;
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
	 * @param angleInRads - angle to turn to in radians
	 */
	@Override
	public void rotateToAngle(double angleInRads) {
		double diff = GBMath.modulo(angleInRads - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angleInRads = getModuleAngle() + diff;

		angleMotor.set(ControlMode.Position,convertRadsToTicks(angleInRads));

		targetAngle = angleInRads;
	}

	/**
	 * @return get the module angle in radians
	 */
	@Override
	//maybe not after gear ratio plz check ~ noam
	public double getModuleAngle() {
		return convertTicksToRads(angleMotor.getSelectedSensorPosition()) ;
	}

	/**
	 * @return returns the current linear motor velocity
	 */
	@Override
	public double getCurrentVelocity() {
		return linearMotor.getSelectedSensorVelocity() * linTicksToMetersPerSecond;
	}

	/**
	 * @param angleInRads - Position to set for the angular encoder (in raw sensor units).
	 */
	@Override
	public void resetEncoderToValue(double angleInRads) {
		angleMotor.setSelectedSensorPosition(convertRadsToTicks(angleInRads));
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
	 * @param speed - double of the wanted speed (m/s) of the linear motor, uses PID and feedForward
	 */
	@Override
	public void setLinSpeed(double speed) {
		linearMotor.set(
				TalonFXControlMode.Velocity,
				speed / linTicksToMetersPerSecond,
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
	 * get the magEncoder's angle raw value
	 * */
	@Override
	public double getAbsoluteEncoderValue() {
		return magEncoder.getAbsolutePosition();
	}

	/**
	 * @param power - double between 1 to -1 to set the percentage of the motors rotation
	 */
	@Override
	public void setRotPowerOnlyForCalibrations(double power) {
		angleMotor.set(ControlMode.PercentOutput, power);
	}
	@Override
	public void setLinPowerOnlyForCalibrations(double power){
		linearMotor.set(ControlMode.PercentOutput, power);
	}
	
}
