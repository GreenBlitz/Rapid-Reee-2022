package edu.greenblitz.pegasus.commands.intake.roller;

public class RunRoller extends RollerCommand {
	@Override
	public void execute() {
		intake.moveRoller(1.0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		intake.stopRoller();
	}
}
