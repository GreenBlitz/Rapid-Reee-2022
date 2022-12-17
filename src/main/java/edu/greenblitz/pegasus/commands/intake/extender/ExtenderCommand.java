package edu.greenblitz.pegasus.commands.intake.extender;

import edu.greenblitz.pegasus.subsystems.Extender;
import edu.greenblitz.pegasus.utils.commands.GBCommand;

public abstract class ExtenderCommand extends GBCommand {
	Extender extender;
	public ExtenderCommand() {
		super();
		extender = Extender.getInstance();
		require(extender);
	}
}
