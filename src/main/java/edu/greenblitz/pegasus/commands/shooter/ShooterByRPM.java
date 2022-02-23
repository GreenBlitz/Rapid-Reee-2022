package edu.greenblitz.pegasus.commands.shooter;

import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.pid.PIDObject;

public class ShooterByRPM extends ShooterCommand {
	protected PIDObject obj;
	protected RemoteCSVTarget logger;
	protected double target;
	protected long tStart;

	public ShooterByRPM(PIDObject obj, double target) {
		this.obj = obj;
		this.obj.setKf(this.obj.getKf() / target);
		this.target = target;
		this.logger = RemoteCSVTarget.initTarget("FlyWheelVel", "time", "vel");
	}

	@Override
	public void initialize() {
		shooter.getPIDController().setIAccum(0);
		shooter.setPIDConsts(obj);
		tStart = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		logger.report((System.currentTimeMillis() - tStart) / 1000.0, shooter.getShooterSpeed());
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
