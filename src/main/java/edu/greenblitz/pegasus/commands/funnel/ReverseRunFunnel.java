package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.pegasus.RobotMap;

public class ReverseRunFunnel extends FunnelCommand {
	@Override
	public void execute() {
		funnel.moveMotor(true);
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stopMotor();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
