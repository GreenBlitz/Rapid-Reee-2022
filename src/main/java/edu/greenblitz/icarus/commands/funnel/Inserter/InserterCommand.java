package edu.greenblitz.icarus.commands.funnel.Inserter;

import edu.greenblitz.icarus.commands.funnel.FunnelCommand;
import edu.greenblitz.icarus.subsystems.Funnel;

public abstract class InserterCommand extends FunnelCommand {

		public InserterCommand() {
			super();
			require(Funnel.getInstance());
	}
}
