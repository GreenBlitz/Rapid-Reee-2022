package edu.greenblitz.pegasus.commands.intake.roller;

public class RunRoller extends RollerCommand{
	@Override
	public void execute() {
		intake.moveRoller();
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
