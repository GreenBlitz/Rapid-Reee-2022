package edu.greenblitz.pegasus.commands.compressor;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Pneumatics;

public abstract class CompressorCommand extends GBCommand {

	protected Pneumatics compressor;

	public CompressorCommand() {
		compressor = Pneumatics.getInstance();
		require(compressor);
	}

}