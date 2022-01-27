package edu.greenblitz.icarus.commands.funnel.Inserter;

import edu.greenblitz.icarus.commands.funnel.FunnelCommand;

public class InserterCommand extends FunnelCommand {
		public InserterCommand() {
			super();
			require(funnel.getPusher());
	}
}
