package edu.greenblitz.pegasus.commands;

public class ShootForTime extends ShooterCommand {
	private double startTime;
	private double TotalTime;
	public ShootForTime(double time){
	this.TotalTime =time;
	}
	
	@Override
	public void initialize() {
		startTime = System.currentTimeMillis()/1000.0;
	}
	
	@Override
	public void execute() {
			shooter.setPower(0.5);
	}
	
	@Override
	public boolean isFinished() {
		return ((System.currentTimeMillis()/1000.0 - startTime) > TotalTime);
	}
}

