package edu.greenblitz.icarus.commands.complexClimb;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.subsystems.Climb;
import edu.greenblitz.icarus.subsystems.ComplexClimb;

public abstract class ComplexClimbCommand extends GBCommand {
	protected ComplexClimb complexClimb;

	public ComplexClimbCommand() {
		require(Climb.getInstance());
		complexClimb = ComplexClimb.getInstance();
	}

}
