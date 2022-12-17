package edu.greenblitz.pegasus.commands.intake.extender;

public class ToggleRoller extends ExtenderCommand {
	@Override
	public void initialize() {
		extender.extend();
	}
	
	@Override
	public void end(boolean interrupted) {
		extender.retract();
	}
	
}
