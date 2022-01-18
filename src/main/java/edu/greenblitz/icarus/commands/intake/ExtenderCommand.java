package edu.greenblitz.icarus.commands.intake;

public abstract class ExtenderCommand extends IntakeCommand {
    public ExtenderCommand() {
        super();
        require(intake.getExtender());
    }
}
