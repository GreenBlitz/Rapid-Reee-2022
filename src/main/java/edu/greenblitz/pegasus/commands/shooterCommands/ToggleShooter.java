package edu.greenblitz.pegasus.commands.shooterCommands;

public class ToggleShooter extends ShooterCommand {
	 public ToggleShooter(){}

	@Override
	public void initialize() {
		shooter.invertPower();
	}
}
