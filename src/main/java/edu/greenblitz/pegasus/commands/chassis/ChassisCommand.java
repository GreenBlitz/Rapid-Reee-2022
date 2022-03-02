package edu.greenblitz.pegasus.commands.chassis;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;

public abstract class ChassisCommand extends GBCommand {
	
	protected Chassis chassis;
	
	public ChassisCommand() {
		super(Chassis.getInstance());
		chassis = Chassis.getInstance();
	}
	
}
