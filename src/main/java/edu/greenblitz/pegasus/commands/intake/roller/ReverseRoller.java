package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.pegasus.RobotMap;

public class ReverseRoller extends RollerCommand{
	@Override
	public void execute() {
		intake.moveRoller(RobotMap.Pegasus.Intake.REVERSE_POWER);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		intake.stop();
	}
}
