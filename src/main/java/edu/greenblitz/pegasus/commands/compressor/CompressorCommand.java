package edu.greenblitz.pegasus.commands.compressor;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Pneumatics;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public abstract class CompressorCommand extends GBCommand {

	protected Pneumatics compressor;

	public CompressorCommand() {
		super(RobotContainer.getInstance().getPneumatics());
		compressor = RobotContainer.getInstance().getPneumatics();
	}

}