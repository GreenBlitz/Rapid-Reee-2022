package edu.greenblitz.pegasus.subsystems.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveModule {


	private int isReversed = 1;
	public double targetAngle;
	public double targetVel;
	private final GBMotor angleMotor;
	private final GBMotor linearMotor;
	private final AnalogInput lamprey;
	private final SimpleMotorFeedforward feedforward;
	private final double wheelCirc;

	public SwerveModule(int portA, int portL, int lampreyID) {
		angleMotor = new SparkMaxFactory().withGearRatio(6).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.angleMotorInverted).generate(portA);
		linearMotor = new SparkMaxFactory().withGearRatio(8).withCurrentLimit(30).withRampRate(0.4).withInverted(RobotMap.Pegasus.Swerve.Module4.INVERTED).generate(portL);
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

	public void rotateToAngle(double angle) {

		double diff = GBMath.modulo(angle - getMotorAngle(), 2 * Math.PI);
		diff -= diff > Math.PI ? 2*Math.PI : 0;
		angle = getMotorAngle() + diff;

		angleMotor.setTargetByPID(angle, AbstractMotor.PIDTarget.Position);
		targetAngle = angle;
	}

	public double getRawLampreyAngle() {
		return lamprey.getValue();
	}

	public double getMotorAngle() {
		return angleMotor.getNormalizedPosition();
	}

	public double getCurrentVel() {
		return linearMotor.getNormalizedVelocity() / 60 * wheelCirc;
	}

	public void rotateByAngle(double angle) {
		angleMotor.setTargetByPID(getMotorAngle() + angle, AbstractMotor.PIDTarget.Position);
	}

	public void resetAngle() {
		angleMotor.resetEncoder();
	}

//	public void resetEncoderByLamprey() {
//		angleMotor.setEncoderAng(getLampreyAngle());
//	}
	
	public void resetEncoderToValue(double angle) {
		angleMotor.setEncoderAng(angle);
	} //todo combine both into same overload
	
	public void resetEncoderToZero(){
		angleMotor.setEncoderAng(0);
	}

	public void configLinPID(PIDObject pidObject) {
		linearMotor.configurePID(pidObject);
	}

	public void configAnglePID(PIDObject pidObject) {
		angleMotor.configurePID(pidObject);
	}


	public void setLinSpeed(double speed) {
		speed *= isReversed;
		linearMotor.setTargetSpeedByPID(speed, feedforward.calculate(speed));
	}

	public void setRotPower(double power) {
		angleMotor.setPower(power);
	}
	//only for debugging

	public double getTargetAngle() {
		return targetAngle;
	}

	public double getTargetVel() {
		return targetVel * isReversed;
	}

	public void setLinPower(double power) {
		linearMotor.setPower(power * isReversed);
	}

	public int getIsReversed() {
		return isReversed;
	}
	
	public SwerveModuleState getModuleState (){
		return new SwerveModuleState(getCurrentVel(),new Rotation2d(this.getMotorAngle()));
	}
	
	public double getLampreyValue(){
		return lamprey.getValue();
	}

	public double getAngMotorTicks(){
		return this.angleMotor.getRawTicks();
	}


}
