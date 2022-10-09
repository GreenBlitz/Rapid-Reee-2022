package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.pegasus.RobotMap;
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
//		SmartDashboard.putNumber("target", target);
//		SmartDashboard.putNumber("inShootingSpeed", inShootingSpeed);
//		SmartDashboard.putNumber("RPM", shooter.getShooterSpeed());
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
