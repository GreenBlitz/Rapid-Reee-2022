package edu.greenblitz.pegasus.commands.limelight;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Limelight;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.Arrays;

public class FindLocation extends GBCommand {
	public FindLocation(){
		super();
		require((Subsystem) Limelight.getInstance());
	}

	@Override
	public void initialize() {
		System.out.println(Arrays.toString(Limelight.getInstance().getLocation()));
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
