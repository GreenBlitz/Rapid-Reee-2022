package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.IMotorFactory;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.pegasus.RobotMap;

public class Shooter extends GBSubsystem {

	private final static double RPM = 3000;
	private int flipped = 1;
	private final GBMotor motor;
	private boolean preparedToShoot;
	private boolean isShooter; //todo

	private static Shooter instance;

	private Shooter(IMotorFactory motorFactory, int id) {
		this.motor = motorFactory.generate(id);
//		//leader.setClosedLoopRampRate(1);
		motor.configurePID(RobotMap.Pegasus.Shooter.ShooterMotor.pid);
//
		preparedToShoot = false;
	}

	public static void create(IMotorFactory motorFactory, int id){
		instance = new Shooter(motorFactory,id);
	}

	public static Shooter getInstance(){
		return instance;
	}

	public void setPower(double power) {
		this.motor.setPower(power);
	}

	public void setIdleMode(AbstractMotor.IdleMode idleMode) {
		motor.setIdleMode(idleMode);
	}

	/**
	 * pid is handled by GBMotor either internal motor controller pid or external in GBMotor implementation
	 *
	 * @param target the target speed in rpm
	 */
	public void setSpeedByPID(double target) {
//		System.out.println(target);
		motor.setTargetSpeedByPID(target * flipped,RobotMap.Pegasus.Shooter.ShooterMotor.feedforward.calculate(target));
	}

	public void setPIDConsts(PIDObject obj) {
		motor.configurePID(obj);
	}

	public double getShooterSpeed() {
		return motor.getRawVelocity();
	}

	public void resetEncoder() {
		motor.resetEncoder();
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
