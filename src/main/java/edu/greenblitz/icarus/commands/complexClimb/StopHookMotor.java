package edu.greenblitz.icarus.commands.complexClimb;

public class StopHookMotor extends ComplexClimbCommand {
	@Override
	public void initialize() {
		complexClimb.safeMoveHookMotor(0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}

