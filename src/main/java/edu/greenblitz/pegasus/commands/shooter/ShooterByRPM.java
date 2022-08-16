package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterByRPM extends ShooterCommand {
	private static final double EPSILON = 50;
	protected PIDObject pidObject;
	//	protected RemoteCSVTarget logger;
	protected double target;
	protected double tStart;
	private int inShootingSpeed;
	
	public ShooterByRPM(PIDObject pidObject, double target) {
		this.pidObject = pidObject;
		this.target = target;
//		this.logger = RemoteCSVTarget.initTarget("FlyWheelVel", "time", "vel");
		this.inShootingSpeed = 0;
	}
	
	
	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
//		shooter.getPIDController().setIAccum(0.0);
		PIDObject temp = new PIDObject(pidObject);
		temp.setFF(RobotMap.Pegasus.Shooter.ShooterMotor.RPM_TO_POWER.linearlyInterpolate(target)[0] / target);
		shooter.setPIDConsts(temp);
		tStart = System.currentTimeMillis() / 1000.0;
	}
	
	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		SmartDashboard.putNumber("target", target);
		SmartDashboard.putNumber("inShootingSpeed", inShootingSpeed);
		SmartDashboard.putNumber("RPM", shooter.getShooterSpeed());

		if (Math.abs(target - shooter.getShooterSpeed()) < EPSILON) {
			this.inShootingSpeed++;
		} else {
			this.inShootingSpeed = 0;
			shooter.setPreparedToShoot(false);
		}
		if (this.inShootingSpeed >= 5) {
			shooter.setPreparedToShoot(true);
		}
//		logger.report((System.currentTimeMillis()/1000.0 - tStart), shooter.getShooterSpeed());
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {
		System.out.println("finished");
		shooter.setPreparedToShoot(false);
		this.inShootingSpeed = 0;
		super.end(interrupted);
	}
}
