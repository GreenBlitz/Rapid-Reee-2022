package edu.greenblitz.pegasus.commands.intake.extender;

public class ToggleRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		intake.toggleExtender();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
