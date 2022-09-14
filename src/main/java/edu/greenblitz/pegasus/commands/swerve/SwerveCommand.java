package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;

public abstract class SwerveCommand extends GBCommand {
	SwerveChassis swerve;

	public SwerveCommand() {
		swerve = SwerveChassis.getInstance();
		require(swerve);
	}

}
