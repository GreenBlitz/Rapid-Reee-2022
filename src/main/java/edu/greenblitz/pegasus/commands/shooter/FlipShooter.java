package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import org.greenblitz.debug.RemoteCSVTarget;

public class FlipShooter extends ShooterCommand {

	public FlipShooter() {}

	@Override
	public void initialize() {
		super.initialize();
		Shooter.getInstance().flip();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
