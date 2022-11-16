package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.PIDObject;

public class Shooter extends GBSubsystem {
	
	private final static double RPM = 3000;
	private int flipped = 1;
	private final CANSparkMax motor;
	private boolean preparedToShoot;
	private boolean isShooter; //todo
	
	private static Shooter instance;
	
	private Shooter(int id) {
		this.motor = new CANSparkMax(id, CANSparkMaxLowLevel.MotorType.kBrushless);
//		//leader.setClosedLoopRampRate(1);
//		this.motor.configurePID(RobotMap.Pegasus.Shooter.ShooterMotor.pid);
		
		//setting the motor pid
		this.motor.getPIDController().setP(RobotMap.Pegasus.Shooter.ShooterMotor.pid.getKp());
		this.motor.getPIDController().setI(RobotMap.Pegasus.Shooter.ShooterMotor.pid.getKi());
		this.motor.getPIDController().setD(RobotMap.Pegasus.Shooter.ShooterMotor.pid.getKd());
		this.motor.getPIDController().setFF(RobotMap.Pegasus.Shooter.ShooterMotor.pid.getKf());
		this.motor.getPIDController().setIZone(RobotMap.Pegasus.Shooter.ShooterMotor.pid.getIZone());
		this.motor.getPIDController().setOutputRange(-RobotMap.Pegasus.Shooter.ShooterMotor.pid.getMaxPower(), RobotMap.Pegasus.Shooter.ShooterMotor.pid.getMaxPower());
		
		preparedToShoot = false;
	}
	
	public static void create(int id) {
		instance = new Shooter(id);
	}
	
	public static Shooter getInstance() {
		return instance;
	}
	
	public void setPower(double power) {
		this.motor.set(power);
	}
	
	public void setIdleMode(CANSparkMax.IdleMode idleMode) {
		motor.setIdleMode(idleMode);
	}
	
	/**
	 * pid is handled by GBMotor either internal motor controller pid or external in GBMotor implementation
	 *
	 * @param target the target speed in rpm
	 */
	public void setSpeedByPID(double target) {
//		System.out.println(target);
		motor.getPIDController().setReference(target * flipped, CANSparkMax.ControlType.kVelocity, 0, RobotMap.Pegasus.Shooter.ShooterMotor.feedforward.calculate(target), SparkMaxPIDController.ArbFFUnits.kVoltage);
		//
	}
	
	public void setPIDConsts(PIDObject obj) {
		this.motor.getPIDController().setP(obj.getKp());
		this.motor.getPIDController().setI(obj.getKi());
		this.motor.getPIDController().setD(obj.getKd());
		this.motor.getPIDController().setFF(obj.getKf());
		this.motor.getPIDController().setIZone(obj.getIZone());
		this.motor.getPIDController().setOutputRange(obj.getMaxPower(), RobotMap.Pegasus.Shooter.ShooterMotor.pid.getMaxPower());
		
	}
	
	public double getShooterSpeed() {
		return motor.getEncoder().getVelocity();
	}
	
	public void resetEncoder() {
		motor.getEncoder().setPosition(0);
	}
	
	public boolean isPreparedToShoot() { //todo make this shooter independent
		return preparedToShoot;
	}
	
	public void setPreparedToShoot(boolean preparedToShoot) {
		this.preparedToShoot = preparedToShoot;
	}
	
	public void flip() {
		this.flipped *= -1;
	}
	
	public int getFlipped() {
		return this.flipped;
	}
	
	
}
