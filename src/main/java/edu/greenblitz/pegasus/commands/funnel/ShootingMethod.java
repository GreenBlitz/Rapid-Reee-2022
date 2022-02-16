package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.pegasus.subsystems.Funnel;

public abstract class ShootingMethod extends FunnelCommand {

	public ShootingMethod() {
		super();
		require(Funnel.getInstance());
	}

}
