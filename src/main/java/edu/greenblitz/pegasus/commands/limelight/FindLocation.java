package edu.greenblitz.pegasus.commands.limelight;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class FindLocation extends GBCommand {
	public FindLocation(){
		super();
	}

	public void initialize() {
		Limelight.getInstance().location();
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
