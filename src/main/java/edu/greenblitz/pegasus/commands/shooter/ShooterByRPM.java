package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterByRPM extends ShooterCommand {
	private double EPSILON = 50;
	protected static int inShootingSpeed;
	private int inShootingSpeedMin = 7;
	protected double target;
	protected double tStart;

	public ShooterByRPM(double target) {
		this.target = target;
		inShootingSpeed = 0;
	}
	
	public ShooterByRPM(double target,double EPSILON, int inShootingSpeedMin ) {
		this(target);
		this.EPSILON = EPSILON;
		this.inShootingSpeedMin = inShootingSpeedMin;
	}


	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
		tStart = System.currentTimeMillis() / 1000.0;
	}

	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
//		SmartDashboard.putNumber("RPM", shooter.getShooterSpeed());
//		SmartDashboard.putNumber("error", Math.abs(shooter.getShooterSpeed() - target)); //show error

		if (Math.abs(shooter.getShooterSpeed() - target) < EPSILON) {
//			shooter.setPreparedToShoot(true);
			inShootingSpeed++;
		} else {
//			shooter.setPreparedToShoot(false);
			inShootingSpeed = 0;
		}

		shooter.setPreparedToShoot(inShootingSpeed > 7);


	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPreparedToShoot(false);
		shooter.setSpeedByPID(0);
		inShootingSpeed = 0;
		super.end(interrupted);
	}
	

	public static int getInShootingSpeed() {
		return inShootingSpeed;
	}
}
