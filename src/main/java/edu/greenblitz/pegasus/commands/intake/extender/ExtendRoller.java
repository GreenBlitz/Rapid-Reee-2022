package edu.greenblitz.pegasus.commands.intake.extender;

public class ExtendRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		extender.extend();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
