package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.pegasus.subsystems.RobotContainer;


public abstract class ShooterCommand extends GBCommand {
	protected Shooter shooter;

	public ShooterCommand() {
		super(RobotContainer.getInstance().getShooter());
		shooter = RobotContainer.getInstance().getShooter();
	}
}
