package edu.greenblitz.pegasus.commands.compressor;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Pneumatics;

public abstract class CompressorCommand extends GBCommand {

    protected Pneumatics compressor;

    public CompressorCommand() {
        super(Pneumatics.getInstance());
        compressor = Pneumatics.getInstance();
    }

}