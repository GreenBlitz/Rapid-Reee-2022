package edu.greenblitz.icarus.commands.climb;

public class StopMotor extends ClimbCommand {

	@Override
	public void initialize() {
		climb.safeMoveMotor(0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
