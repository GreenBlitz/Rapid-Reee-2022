package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

public class isPreparedToShoot extends GBCommand {

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {

	}

	@Override
	public boolean isFinished() {
		return Shooter.getInstance().isPreparedToShoot();
	}

	@Override
	public void end(boolean interrupted) {
	}

}


