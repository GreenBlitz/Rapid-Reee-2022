package edu.greenblitz.icarus.commands.intake;

public abstract class RollerCommand extends IntakeCommand {
    public RollerCommand() {
        super();
        require(intake.getRoller());
    }
}
