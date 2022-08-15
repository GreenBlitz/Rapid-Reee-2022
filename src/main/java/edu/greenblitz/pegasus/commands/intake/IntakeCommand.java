package edu.greenblitz.pegasus.commands.intake;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Intake;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class IntakeCommand extends GBCommand {
	protected Intake intake;

	public IntakeCommand() {
		intake = RobotContainer.getInstance().getIntake();
	}
}
