package edu.greenblitz.pegasus.subsystems.swerve;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.motors.GBFalcon;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SdsSwerveModule implements SwerveModule{
	public static final double ANG_GEAR_RATIO = (150.0/7); //   input/output
	public static final double LIN_GEAR_RATIO = 8.14;

	public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI; //very accurate right now
	public static final double linTicksToMeters = RobotMap.Pegasus.General.Motors.FALCON_TICKS_PER_RADIAN * WHEEL_CIRC / LIN_GEAR_RATIO;
	public static final double angleTicksToWheelToRPM = RobotMap.Pegasus.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
	public static final double linTicksToMetersPerSecond = RobotMap.Pegasus.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM / LIN_GEAR_RATIO * WHEEL_CIRC / 60;
	public static final double angleTicksToRadians = RobotMap.Pegasus.General.Motors.FALCON_TICKS_PER_RADIAN / ANG_GEAR_RATIO;
	public static final double magEncoderTicksToFalconTicks = 2*Math.PI/angleTicksToRadians;

	public static final PIDObject angPID = new PIDObject().withKp(0.05).withMaxPower(1.0).withFF(0);//.withKd(10).withMaxPower(0.8);
	private static final GBFalcon.FalconConfObject baseAngConfObj =
			new GBFalcon.FalconConfObject()
					.withNeutralMode(NeutralMode.Brake)
					.withCurrentLimit(30)
					.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
					.withVoltageCompSaturation(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
					.withInverted(true)
					.withPID(angPID);

	public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
	private static final GBFalcon.FalconConfObject baseLinConfObj =
			new GBFalcon.FalconConfObject()
					.withNeutralMode(NeutralMode.Brake)
					.withCurrentLimit(40)
					.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
					.withVoltageCompSaturation(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
					.withPID(linPID);


	public double targetAngle;
	public double targetVel;
	private GBFalcon angleMotor;
	private GBFalcon linearMotor;
	private DutyCycleEncoder magEncoder;
	private SimpleMotorFeedforward feedforward;

	public SdsSwerveModule(int angleMotorID, int linearMotorID, int AbsoluteEncoderID, boolean linInverted,double magEncoderOffset) {
		//SET ANGLE MOTO
		angleMotor = new GBFalcon(angleMotorID);
		angleMotor.config(new GBFalcon.FalconConfObject(baseAngConfObj));


		linearMotor = new GBFalcon(linearMotorID);
		linearMotor.config(new GBFalcon.FalconConfObject(baseLinConfObj).withInverted(linInverted));

		magEncoder = new DutyCycleEncoder(AbsoluteEncoderID);
		this.magEncoder.setPositionOffset(magEncoderOffset);
		SmartDashboard.putNumber("lol", magEncoder.getPositionOffset());

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
	public void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module) {
		angleMotor.setSelectedSensorPosition(getAbsoluteEncoderValue() * magEncoderTicksToFalconTicks);
	}

	@Override
	public void configLinPID(PIDObject pidObject) {
		linearMotor.configPID(pidObject);
	}

	@Override
	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configPID(pidObject);
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
	 * get the magEncoder's angle value in rotations
	 * */
	@Override
	public double getAbsoluteEncoderValue() {
		return magEncoder.get();
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
