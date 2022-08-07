package edu.greenblitz.pegasus.commands.shooterCommands;

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
