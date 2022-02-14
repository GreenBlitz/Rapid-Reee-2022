package edu.greenblitz.icarus.commands.funnel;

import edu.greenblitz.icarus.subsystems.GBSubsystem;

public class InsertByConstants extends FunnelCommand {
	private double power;

	public InsertByConstants(double power) {
		super();
		this.power = power;
	}

	@Override
	public void execute() {
		funnel.moveMotor(power);
	}

	@Override
	public void end(boolean interrupted) {
		funnel.moveMotor(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

}
