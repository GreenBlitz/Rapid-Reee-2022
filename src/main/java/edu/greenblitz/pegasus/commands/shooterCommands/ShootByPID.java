package edu.greenblitz.pegasus.commands.shooterCommands;

public class ShootByPID extends ShooterCommand{
	private double kp, ki, kd, ff, target;


	public ShootByPID(double kp, double ki, double kd, double ff, double target){
		this.kp = kp;
		this.ki = ki;
		this.kd = kd;
		this.ff = ff;
		this.target = target;
	}
	public ShootByPID(double target){
		this(0, 0, 0, 0.3, target);
	}

	@Override
	public void execute() {
		System.out.println("In Shoot By PID");
		shooter.setSpeedByPID(kp, ki, kd, ff, target);
	}

	@Override
	public void end(boolean interrupted) {
		shooter.stopPID();
	}
}
