package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShootByRPM2 extends ShooterCommand{
	private static final double EPSILON = 50;
	protected PIDObject pidObject;
	//	protected RemoteCSVTarget logger;
	protected double target;
	protected double tStart;
	private int inShootingSpeed;

	public ShootByRPM2(double target) {
		this.target = target;
	}

	@Override
	public void execute() {
		shooter.setSpeedByPID(target);
		SmartDashboard.putNumber("ShooterSpeed", shooter.getShooterSpeed());
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setSpeedByPID(0);
	}
}
