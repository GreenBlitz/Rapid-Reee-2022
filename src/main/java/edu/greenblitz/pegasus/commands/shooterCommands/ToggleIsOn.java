package edu.greenblitz.pegasus.commands.shooterCommands;

public class ToggleIsOn extends ShooterCommand{
	public ToggleIsOn(){

	}

	@Override
	public void initialize() {
		shooter.toggle();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
