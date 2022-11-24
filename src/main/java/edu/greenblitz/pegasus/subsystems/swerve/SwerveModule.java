package edu.greenblitz.pegasus.subsystems.swerve;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.ControlType;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.swerveKinematics.Vector;
import edu.greenblitz.pegasus.utils.swerveKinematics.Point;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;

public class SwerveModule {

	private Vector transform;
	private double angularVelocity;
	private Vector finalVector;
	private Point position;
	private double baseAngle;

	private int isReversed = 1;
	public double targetAngle;
	public double targetVel;
	private final CANSparkMax angleMotor;
	private final CANSparkMax linearMotor;
	private final AnalogInput lamprey;
	private final SimpleMotorFeedforward feedforward;
	private final double wheelCirc;

	public SwerveModule(int portA, int portL, int lampreyID, boolean linInverted, Point position, double baseRotationAngle) {
//		angleMotor = new SparkMaxFactory().withGearRatio(6)
		//SET ANGLE MOTOR

		this.baseAngle = baseRotationAngle;
		this.position = position;
		this.angularVelocity = 0;
		this.finalVector = new Vector();

		angleMotor = new CANSparkMax(portA, CANSparkMaxLowLevel.MotorType.kBrushless);
		angleMotor.setSmartCurrentLimit(30);
		angleMotor.setClosedLoopRampRate(0.4);//todo is closed or open?
		angleMotor.setInverted(RobotMap.Pegasus.Swerve.angleMotorInverted);
		angleMotor.getEncoder().setPositionConversionFactor(2 * Math.PI * RobotMap.Pegasus.Swerve.ANG_GEAR_RATIO);
		angleMotor.getEncoder().setVelocityConversionFactor(RobotMap.Pegasus.Swerve.ANG_GEAR_RATIO);
		
		linearMotor = new CANSparkMax(portL, CANSparkMaxLowLevel.MotorType.kBrushless);
		linearMotor.setSmartCurrentLimit(30);
		linearMotor.setClosedLoopRampRate(0.4); //todo is closed or open?
		linearMotor.setInverted(linInverted);
		
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

		angleMotor.getPIDController().setReference(angle, ControlType.kPosition);
		targetAngle = angle;
	}

	public double getRawLampreyAngle() {
		return lamprey.getValue();
	}

	public double getMotorAngle() {
		return angleMotor.getEncoder().getPosition() / RobotMap.Pegasus.Swerve.angleTicksToRadians;
	}

	public double getCurrentVel() {
		return (linearMotor.getEncoder().getVelocity() / RobotMap.Pegasus.Swerve.linTicksToWheelToRPM) / 60 * wheelCirc;
	}

	public void rotateByAngle(double angle) {
		angleMotor.getPIDController().setReference(getMotorAngle() + angle, ControlType.kPosition);
	}

	public void resetAngle() {
		angleMotor.getEncoder().setPosition(0);
	}

//	public void resetEncoderByLamprey() {
//		angleMotor.setEncoderAng(getLampreyAngle());
//	}
	
	public void resetEncoderToValue(double angle) {
		angleMotor.getEncoder().setPosition(angle);
	} //todo combine both into same overload
	
	public void resetEncoderToZero(){
		angleMotor.getEncoder().setPosition(0);
	}

	public void configLinPID(PIDObject pidObject) {
		linearMotor.getPIDController().setP(pidObject.getKp());
		linearMotor.getPIDController().setI(pidObject.getKi());
		linearMotor.getPIDController().setD(pidObject.getKd());
		linearMotor.getPIDController().setFF(pidObject.getKf());
		linearMotor.getPIDController().setIZone(pidObject.getIZone());
		linearMotor.getPIDController().setOutputRange(-pidObject.getMaxPower(), pidObject.getMaxPower());
	}

	public void configAnglePID(PIDObject pidObject) {
		angleMotor.getPIDController().setP(pidObject.getKp());
		angleMotor.getPIDController().setI(pidObject.getKi());
		angleMotor.getPIDController().setD(pidObject.getKd());
		angleMotor.getPIDController().setFF(pidObject.getKf());
		angleMotor.getPIDController().setIZone(pidObject.getIZone());
		angleMotor.getPIDController().setOutputRange(-pidObject.getMaxPower(), pidObject.getMaxPower());
	}


	public void setLinSpeed(double speed) {
		speed *= isReversed;
		linearMotor.getPIDController().setReference(speed,ControlType.kPosition, 0, feedforward.calculate(speed));
	}

	public void setRotPower(double power) {
		angleMotor.set(power);
	}
	//only for debugging

	public double getTargetAngle() {
		return targetAngle;
	}

	public double getTargetVel() {
		return targetVel * isReversed;
	}

	public void setLinPower(double power) {
		linearMotor.set(power * isReversed);
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
		return this.angleMotor.getEncoder().getPosition();
	}


	/** alternate swerve kinematics atan & noam*/

	public void setModuleState (SwerveModuleState state){

		if(state.speedMetersPerSecond < 0.001){
			return;
		}

		this.rotateToAngle(state.angle.getRadians());
		this.setLinSpeed(state.speedMetersPerSecond);
	}

	public void update(){
		Vector ang = new Vector(this.baseAngle,angularVelocity,this.position);
		//transform.setPos(getPos());
		finalVector = Vector.add(ang,transform);
		setModuleState(new SwerveModuleState(
				finalVector.getMagnitude(),
				new Rotation2d(finalVector.getDirection())
		));
	}

	public Vector getTransform() {return transform;}

	public void setTransform(Vector transform) {this.transform = transform;}

	public double getAngularVelocity() {return this.angularVelocity;}

	public void setAngularVelocity(double angVel) {this.angularVelocity = angVel;}

	public Point getPosition() {return this.position;}

	public Vector getFinalVector() {return this.finalVector;}


}
