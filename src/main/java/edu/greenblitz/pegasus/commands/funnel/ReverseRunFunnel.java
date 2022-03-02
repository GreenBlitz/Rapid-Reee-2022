package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.pegasus.RobotMap;

public class ReverseRunFunnel extends FunnelCommand {
	@Override
	public void execute() {
		funnel.moveMotor(RobotMap.Pegasus.Funnel.REVERSE_POWER);
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stop();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
