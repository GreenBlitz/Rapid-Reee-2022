package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterByRPM extends ShooterCommand {
	private double EPSILON = 50; //todo make static final
	private static final double APPROXIMATE_INSERTION_TIME = 5; //approximate amount of roborio cycles between funnel activation and shooting
	private double lastSpeed;
	protected double target;
	protected double tStart;

	public ShooterByRPM(double target) {
		this.target = target;
	}
	
	public ShooterByRPM(double target,double EPSILON, int inShootingSpeedMin ) {
		this(target);
		this.EPSILON = EPSILON;
	}


	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
		tStart = System.currentTimeMillis() / 1000.0;
		lastSpeed = shooter.getShooterSpeed();
	}

	double accel;
	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		accel = lastSpeed - shooter.getShooterSpeed();

		shooter.setPreparedToShoot((Math.abs(shooter.getShooterSpeed() - target) < EPSILON
				&& Math.abs(shooter.getShooterSpeed() - target + (accel*APPROXIMATE_INSERTION_TIME)) < EPSILON));
		lastSpeed = shooter.getShooterSpeed();

	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPreparedToShoot(false);
		shooter.setSpeedByPID(0);  // todo find a solution that allows for double shoot
		super.end(interrupted);
	}
	

}
