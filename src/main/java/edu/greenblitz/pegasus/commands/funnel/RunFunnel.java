package edu.greenblitz.pegasus.commands.funnel;

public class RunFunnel extends FunnelCommand {
	@Override
	public void execute() {
		funnel.move();
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
