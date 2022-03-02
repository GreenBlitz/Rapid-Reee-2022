package edu.greenblitz.pegasus.commands.intake.roller;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Intake;

public class DisableRoller extends RollerCommand{
	@Override
	public void execute() {
		Intake.getInstance().stop();
	}
}
