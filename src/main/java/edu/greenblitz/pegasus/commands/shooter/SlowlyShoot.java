package edu.greenblitz.pegasus.commands.shooter;

public class SlowlyShoot extends ShooterCommand {
	@Override
	public void execute() {
		shooter.shoot(0.3);
	}

	@Override
	public void end(boolean interrupted) {
		shooter.shoot(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
