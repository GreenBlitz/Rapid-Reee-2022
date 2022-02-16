package edu.greenblitz.pegasus.commands.funnel.Inserter;

import edu.greenblitz.pegasus.commands.funnel.FunnelCommand;
import edu.greenblitz.pegasus.subsystems.Funnel;

public abstract class InserterCommand extends FunnelCommand {

		public InserterCommand() {
			super();
			require(Funnel.getInstance());
	}
}
