package edu.greenblitz.pegasus.subsystems.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.Dataset;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.General.Motors.NEO_PHYSICAL_TICKS_TO_RADIANS;

public class KazaSwerveModule implements SwerveModule {
	public static final double ANG_GEAR_RATIO = 6.0;
	public static final double LIN_GEAR_RATIO = 8.0;


	public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI; //very accurate right now
	public static final double linTicksToMeters = RobotMap.Pegasus.General.Motors.SPARKMAX_TICKS_PER_RADIAN * WHEEL_CIRC / LIN_GEAR_RATIO;
	public static final double angleTicksToWheelToRPM = RobotMap.Pegasus.General.Motors.SPARKMAX_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
	public static final double linTicksToMetersPerSecond = RobotMap.Pegasus.General.Motors.SPARKMAX_VELOCITY_UNITS_PER_RPM * WHEEL_CIRC / 60 / LIN_GEAR_RATIO;
	public static final double angleTicksToRadians = RobotMap.Pegasus.General.Motors.SPARKMAX_TICKS_PER_RADIAN / ANG_GEAR_RATIO;

	public static final PIDObject angPID = new PIDObject().withKp(0.5).withMaxPower(1.0);//.withKd(10).withMaxPower(0.8);
	private static final GBSparkMax.SparkMaxConfObject baseAngConfObj =
			new GBSparkMax.SparkMaxConfObject()
					.withIdleMode(CANSparkMax.IdleMode.kBrake)
					.withCurrentLimit(30)
					.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
					.withVoltageComp(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
					.withInverted(true)
					.withPID(angPID)
					.withPositionConversionFactor(angleTicksToRadians)
					.withVelocityConversionFactor(angleTicksToWheelToRPM);

	public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
	private static final GBSparkMax.SparkMaxConfObject baseLinConfObj =
			new GBSparkMax.SparkMaxConfObject()
					.withIdleMode(CANSparkMax.IdleMode.kBrake)
					.withCurrentLimit(40)
					.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
					.withVoltageComp(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
					.withPID(linPID)
					.withPositionConversionFactor(linTicksToMeters)
					.withVelocityConversionFactor(linTicksToMetersPerSecond);




	public double targetAngle;
	public double targetVel;
	private GBSparkMax angleMotor;
	private GBSparkMax linearMotor;
	private AnalogInput lamprey;
	private SimpleMotorFeedforward feedforward;


	public KazaSwerveModule(int angleMotorID, int linearMotorID, int lampreyID, boolean linInverted) {
		//SET ANGLE MOTOR
		angleMotor = new GBSparkMax(angleMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		angleMotor.config(baseAngConfObj);

		linearMotor = new GBSparkMax(linearMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		linearMotor.config(baseLinConfObj.withInverted(linInverted));

		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(2);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);;
	}

//	public double getLampreyAngle() { // in radians;
//		int val = lamprey.getValue();
//		if(val < minLampreyVal) minLampreyVal = val;
//		if(val > maxLampreyVal) maxLampreyVal = val;
//		return (lamprey.getValue() - minLampreyVal) / (maxLampreyVal - minLampreyVal) * Math.PI * 2;
//	}


	/** sets to module to be at the given module state */
	@Override
	public void setModuleState(SwerveModuleState moduleState) {
		setLinSpeed(moduleState.speedMetersPerSecond);
		rotateToAngle(moduleState.angle.getRadians());
	}

	@Override
	public void rotateToAngle(double angle) {

		double diff = GBMath.modulo(angle - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angle = getModuleAngle() + diff;

		angleMotor.getPIDController().setReference(angle, ControlType.kPosition);

		targetAngle = angle;
	}


	/** get the module angle by radians */
	@Override
	public double getModuleAngle() {
		return angleMotor.getEncoder().getPosition();
	}

	@Override
	public double getCurrentVelocity() {
		return (linearMotor.getEncoder().getVelocity() / linTicksToMetersPerSecond);
	}



	/** resetEncoderToValue - reset the angular encoder to RADIANS */
	@Override
	public void resetEncoderToValue(double angle) {
		angleMotor.getEncoder().setPosition(angle);
	}
	
	@Override
	public void resetEncoderToValue(){
		angleMotor.getEncoder().setPosition(0);
	}


	@Override
	public void resetEncoderByAbsoluteEncoder(SwerveChassis.Module module){
		resetEncoderToValue(Calibration.CALIBRATION_DATASETS.get(module).linearlyInterpolate(getAbsoluteEncoderValue())[0] * NEO_PHYSICAL_TICKS_TO_RADIANS);
	}
	@Override
	public void configLinPID(PIDObject pidObject) {
		linearMotor.configPID(pidObject);
	}

	@Override
	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configPID(pidObject);
	}


	@Override
	public void setLinSpeed(double speed) {
		linearMotor.getPIDController().setReference(speed,ControlType.kVelocity, 0, feedforward.calculate(speed));
	}

	@Override
	public void stop(){
		angleMotor.set(0);
		linearMotor.set(0);
	}

	//only for debugging

	@Override
	public double getTargetAngle() {
		return targetAngle;
	}

	@Override
	public double getTargetVel() {
		return targetVel;
	}

	@Override
	public SwerveModuleState getModuleState(){
		return new SwerveModuleState(getCurrentVelocity(),new Rotation2d(this.getModuleAngle()));
	}

	/** get the lamprey's angle raw voltage*/
	@Override
	public double getAbsoluteEncoderValue(){
		return lamprey.getVoltage();
	}
	@Override
	public void setRotPowerOnlyForCalibrations(double power){
		angleMotor.set(power);
	}
	@Override
	public void setLinPowerOnlyForCalibrations(double power){
		linearMotor.set(power);
	}


}
