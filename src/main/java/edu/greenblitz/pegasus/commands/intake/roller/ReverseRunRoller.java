package edu.greenblitz.pegasus.commands.intake.roller;

public class ReverseRunRoller extends RollerCommand{
	@Override
	public void execute() {
		intake.moveRoller(true);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
<<<<<<< HEAD:src/main/java/edu/greenblitz/pegasus/commands/intake/roller/ReverseRoller.java
		intake.stopRoller();
=======
		intake.stopMotor();
>>>>>>> bf5067f (asaf - refactored code to follow similar move motor conventions):src/main/java/edu/greenblitz/pegasus/commands/intake/roller/ReverseRunRoller.java
	}
}
