package edu.greenblitz.pegasus.commands.complexClimb;

public class StopTurningMotor extends ComplexClimbCommand {
	@Override
	public void initialize() {
		complexClimb.safeMoveTurningMotor(0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
