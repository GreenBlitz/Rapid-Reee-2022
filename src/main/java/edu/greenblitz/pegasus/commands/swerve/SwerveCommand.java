package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class SwerveCommand extends GBCommand {
	SwerveChassis swerve;
	public SwerveCommand() {
		swerve = RobotContainer.getInstance().getSwerve();
		require(RobotContainer.getInstance().getSwerve());
	}

}
