package edu.greenblitz.icarus.commands.intake.extender;

import edu.greenblitz.icarus.commands.intake.IntakeCommand;

public abstract class ExtenderCommand extends IntakeCommand {
    public ExtenderCommand() {
        super();
        require(intake.getExtender());
    }
}
