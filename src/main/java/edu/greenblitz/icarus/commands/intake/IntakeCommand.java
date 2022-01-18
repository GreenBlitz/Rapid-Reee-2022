package edu.greenblitz.icarus.commands.intake;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.subsystems.Intake;

public abstract class IntakeCommand extends GBCommand {
    protected Intake intake;

    public IntakeCommand() {
        intake = Intake.getInstance();
    }
}
