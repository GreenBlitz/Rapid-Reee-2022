package edu.greenblitz.pegasus.commands;

public class ToggleShooter extends ShooterCommand {

	@Override
	public void initialize() {
		shooter.toggle();
		System.out.println("damn");
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
