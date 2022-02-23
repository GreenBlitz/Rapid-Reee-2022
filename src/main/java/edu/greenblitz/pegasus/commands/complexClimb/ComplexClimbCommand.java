package edu.greenblitz.pegasus.commands.complexClimb;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.ComplexClimb;

public abstract class ComplexClimbCommand extends GBCommand {
	protected ComplexClimb complexClimb;

	public ComplexClimbCommand() {
		require(ComplexClimb.getInstance());
		complexClimb = ComplexClimb.getInstance();
	}

}
