package edu.greenblitz.icarus.commands.funnel;

import edu.greenblitz.icarus.subsystems.Funnel;

public abstract class ShootingMethod extends FunnelCommand {

	public ShootingMethod() {
		super();
		require(Funnel.getInstance());
	}

}
