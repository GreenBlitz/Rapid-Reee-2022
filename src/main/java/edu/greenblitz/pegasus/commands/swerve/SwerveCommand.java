package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.base.GBCommand;

public abstract class SwerveCommand extends GBCommand {
	protected ModuleTest moduleTest;

	public SwerveCommand() {
		moduleTest = ModuleTest.getInstance();
		require(moduleTest);
	}

}
