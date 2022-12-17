package edu.greenblitz.pegasus.commands.intake.extender;

public class RetractRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		extender.retract();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
