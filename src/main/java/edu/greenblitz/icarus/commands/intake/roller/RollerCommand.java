package edu.greenblitz.icarus.commands.intake.roller;

import edu.greenblitz.icarus.commands.intake.IntakeCommand;

public abstract class RollerCommand extends IntakeCommand {
    public RollerCommand() {
        super();
        require(intake.getRoller());
    }
}
