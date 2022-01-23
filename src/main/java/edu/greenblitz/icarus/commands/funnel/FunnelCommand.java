package edu.greenblitz.icarus.commands.funnel;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.subsystems.Funnel;

public class FunnelCommand extends GBCommand {
	
	protected Funnel funnel;
	
	public FunnelCommand() {
		funnel = Funnel.getInstance();
	}
}
