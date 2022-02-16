package edu.greenblitz.pegasus.commands.complexClimb;

public class StopMotor extends ComplexClimbCommand {
	@Override
	public void initialize() {
		complexClimb.moveCurrentMotor(0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
