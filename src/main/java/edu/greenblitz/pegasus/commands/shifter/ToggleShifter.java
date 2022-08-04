package edu.greenblitz.pegasus.commands.shifter;


public class ToggleShifter extends ShifterCommand {
	@Override
	public void initialize() {
		shifter.toggleShift();
	}
}
