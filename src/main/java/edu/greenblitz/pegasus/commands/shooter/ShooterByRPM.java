package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShooterByRPM extends ShooterCommand {
	private static final double EPSILON = 50;
	protected double target;
	protected double tStart;
	private int inShootingSpeed;

	public ShooterByRPM(double target) {
		this.target = target;
		this.inShootingSpeed = 0;
	}


	@Override
	public void initialize() {
		shooter.setPreparedToShoot(false);
		tStart = System.currentTimeMillis() / 1000.0;
	}

	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		SmartDashboard.putNumber("RPM", shooter.getShooterSpeed());
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
		System.out.println("finished");
		shooter.setPreparedToShoot(false);
		shooter.setSpeedByPID(0);
//		this.inShootingSpeed = 0;
		super.end(interrupted);
	}
}
