package edu.greenblitz.icarus.commands.funnel;

public class StopInserter extends ShootingMethod {

	@Override
	public void initialize() {
		funnel.moveMotor(0);
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
