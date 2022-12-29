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

public class SwerveModule {


	public double targetAngle;
	public double targetVel;
	private SwerveModuleState lastModuleState = new SwerveModuleState(); //part of NaN protection
	private GBSparkMax angleMotor;
	private GBSparkMax linearMotor;
	private AnalogInput lamprey;
	private SimpleMotorFeedforward feedforward;
	private double wheelCirc;


	public SwerveModule (int angleMotorID, int linearMotorID, int lampreyID, GBSparkMax.SparkMaxConfObject angConfObj, GBSparkMax.SparkMaxConfObject linConfObj) {
		//SET ANGLE MOTOR
		angleMotor = new GBSparkMax(angleMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		angleMotor.config(angConfObj);

		linearMotor = new GBSparkMax(linearMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
		linearMotor.config(linConfObj);
		
		lamprey = new AnalogInput(lampreyID);
		lamprey.setAverageBits(2);
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
	public void setModuleState(SwerveModuleState moduleState) {
		lastModuleState = moduleState;
		setLinSpeed(moduleState.speedMetersPerSecond);
		rotateToAngle(moduleState.angle.getRadians());
	}

	private void rotateToAngle(double angle) {

		double diff = GBMath.modulo(angle - getModuleAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angle = getModuleAngle() + diff;

		angleMotor.getPIDController().setReference(angle, ControlType.kPosition);

		targetAngle = angle;
	}


	/** get the module angle by radians */
	public double getModuleAngle() {
		return angleMotor.getEncoder().getPosition();
	}

	public double getCurrentVelocity() {
		return (linearMotor.getEncoder().getVelocity());
	}

	public void rotateByAngle(double angle) {
		angleMotor.getPIDController().setReference(getModuleAngle() + angle, ControlType.kPosition);
	}

//	public void resetEncoderByLamprey() {
//		angleMotor.setEncoderAng(getLampreyAngle());
//	}

	/** resetEncoderToValue - reset the angular encoder to RADIANS */
	public void resetEncoderToValue(double angle) {
		angleMotor.getEncoder().setPosition(angle);
	}
	
	public void resetEncoderToValue(){
		angleMotor.getEncoder().setPosition(0);
	}

	public void resetEncoderByLamprey(Dataset dataset){
		resetEncoderToValue(dataset.linearlyInterpolate(getLampreyVoltage())[0] * RobotMap.Pegasus.Swerve.NEO_PHYSICAL_TICKS_TO_RADIANS);
	}

	public void configLinPID(PIDObject pidObject) {
		linearMotor.configPID(pidObject);
	}

	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configPID(pidObject);
	}

	private void setLinSpeed(double speed) {
		linearMotor.getPIDController().setReference(speed,ControlType.kVelocity, 0, feedforward.calculate(speed));
	}

	public void stop (){
		angleMotor.set(0);
		linearMotor.set(0);
	}

	//only for debugging

	public double getTargetAngle() {
		return targetAngle;
	}

	public SwerveModuleState getModuleState (){
		if(Double.isNaN(this.getModuleAngle())){ //NaN protection
			return lastModuleState;
		}
		return new SwerveModuleState(getCurrentVelocity(),new Rotation2d(this.getModuleAngle()));
		
	}

	/** get the lamprey's angle raw units (analog to digital converter)*/
	public double getLampreyVoltage(){
		return lamprey.getVoltage();
	}
	public void setRotPowerOnlyForCalibrations(double power){
		angleMotor.set(power);
	}


}
