package edu.greenblitz.icarus.commands.chassis;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.subsystems.Chassis;

public abstract class ChassisCommand extends GBCommand {
	
	protected Chassis chassis;
	
	public ChassisCommand() {
		super(Chassis.getInstance());
		chassis = Chassis.getInstance();
	}
	
}
