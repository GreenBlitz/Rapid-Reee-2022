package edu.greenblitz.icarus.commands.shooter;

import org.greenblitz.debug.RemoteCSVTarget;

public class ShootByConstant extends ShooterCommand {

	protected double power;
	protected RemoteCSVTarget logger;
	protected long tStart;

	public ShootByConstant(double power) {
		this.power = power;
		this.logger = RemoteCSVTarget.initTarget("FlyWheelRPM2Power", "time", "vel");
	}

	@Override
	public void initialize() {
		super.initialize();
		tStart = System.currentTimeMillis();
	}

	@Override
	public void execute() {
		shooter.shoot(power);
		logger.report((System.currentTimeMillis() - tStart) / 1000.0, shooter.getShooterSpeed());
	}

	@Override
	public void end(boolean interrupted) {
		shooter.shoot(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
