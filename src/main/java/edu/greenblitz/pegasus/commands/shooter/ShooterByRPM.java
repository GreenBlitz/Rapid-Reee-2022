package edu.greenblitz.pegasus.commands.shooter;

import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.pid.PIDObject;

public class ShooterByRPM extends ShooterCommand {
	protected PIDObject obj;
	protected RemoteCSVTarget logger;
	protected double target;
	protected long tStart;
	private double epsilon = 10;

	public ShooterByRPM(PIDObject obj, double target) {
		this.obj = obj;
		this.obj.setKf(this.obj.getKf() / target);
		this.target = target;
		this.logger = RemoteCSVTarget.initTarget("FlyWheelVel", "time", "vel");
	}

	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
		shooter.getPIDController().setIAccum(0.0);
		shooter.setPIDConsts(obj);
		tStart = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		if(Math.abs(target - shooter.getShooterSpeed()) < epsilon ){
			shooter.setPreparedToShoot(true);
		}
		logger.report((System.currentTimeMillis() - tStart) / 1000.0, shooter.getShooterSpeed());
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		shooter.setPreparedToShoot(false);
	}
}
