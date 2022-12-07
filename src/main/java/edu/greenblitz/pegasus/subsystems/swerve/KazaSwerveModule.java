package edu.greenblitz.pegasus.subsystems.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

public class KazaSwerveModule implements SwerveModule {


	public double targetAngle;
	public double targetVel;
	private CANSparkMax angleMotor;
	private CANSparkMax linearMotor;
	private AnalogInput lamprey;
	private SimpleMotorFeedforward feedforward;
	private double wheelCirc;

	public KazaSwerveModule(int angleMotorID, int linearMotorID, int lampreyID, boolean linInverted) {
		//SET ANGLE MOTO
		angleMotor = new CANSparkMax(angleMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		angleMotor.setSmartCurrentLimit(30);
		angleMotor.setClosedLoopRampRate(0.4);
		angleMotor.setInverted(RobotMap.Pegasus.Swerve.angleMotorInverted);

		angleMotor.getEncoder().setPositionConversionFactor(RobotMap.Pegasus.Swerve.angleTicksToRadians);
		angleMotor.getEncoder().setVelocityConversionFactor(RobotMap.Pegasus.Swerve.ANG_GEAR_RATIO);

		linearMotor = new CANSparkMax(linearMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		linearMotor.setSmartCurrentLimit(30);
		linearMotor.setClosedLoopRampRate(0.4);
		linearMotor.setOpenLoopRampRate(0.4);
		linearMotor.setInverted(linInverted);
		linearMotor.getEncoder().setPositionConversionFactor(RobotMap.Pegasus.Swerve.linTicksToMeters);
		
		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(2);
		configAnglePID(RobotMap.Pegasus.Swerve.angPID);
		configLinPID(RobotMap.Pegasus.Swerve.linPID);
		this.feedforward = new SimpleMotorFeedforward(RobotMap.Pegasus.Swerve.ks, RobotMap.Pegasus.Swerve.kv, RobotMap.Pegasus.Swerve.ka);;
		this.wheelCirc = RobotMap.Pegasus.Swerve.WHEEL_CIRC;
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
		return (linearMotor.getEncoder().getVelocity() / RobotMap.Pegasus.Swerve.linTicksToWheelToRPM);
	}



	/** resetEncoderToValue - reset the angular encoder to RADIANS */
	@Override
	public void resetEncoderToValue(double angle) {
		angleMotor.getEncoder().setPosition(angle);
	} //todo combine both into same overload
	
	@Override
	public void resetEncoderToValue(){
		angleMotor.getEncoder().setPosition(0);
	}

	@Override
	public void configLinPID(PIDObject pidObject) {
		linearMotor.getPIDController().setP(pidObject.getKp());
		linearMotor.getPIDController().setI(pidObject.getKi());
		linearMotor.getPIDController().setD(pidObject.getKd());
		linearMotor.getPIDController().setFF(pidObject.getKf());
		linearMotor.getPIDController().setIZone(pidObject.getIZone());
		linearMotor.getPIDController().setOutputRange(-pidObject.getMaxPower(), pidObject.getMaxPower());
	}

	@Override
	public void configAnglePID(PIDObject pidObject) {
		angleMotor.getPIDController().setP(pidObject.getKp());
		angleMotor.getPIDController().setI(pidObject.getKi());
		angleMotor.getPIDController().setD(pidObject.getKd());
		angleMotor.getPIDController().setFF(pidObject.getKf());
		angleMotor.getPIDController().setIZone(pidObject.getIZone());
		angleMotor.getPIDController().setOutputRange(-pidObject.getMaxPower(), pidObject.getMaxPower());
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

	/** get the lamprey's angle raw units (analog to digital converter)*/
	@Override
	public double getAbsoluteEncoderValue(){
		return lamprey.getValue();
	}
	@Override
	public void setRotPowerOnlyForCalibrations(double power){
		angleMotor.set(power);
	}


}
