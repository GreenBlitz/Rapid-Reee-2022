package edu.greenblitz.pegasus.commands;

public class ToggleShooter extends ShooterCommand{
	
	@Override
	public void initialize() {
		shooter.toggle();
	}
	
	@Override
	public boolean isFinished() {
		return true;
	}
}
