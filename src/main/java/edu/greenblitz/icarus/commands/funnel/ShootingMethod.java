package edu.greenblitz.icarus.commands.funnel;

public abstract class ShootingMethod  extends FunnelCommand{
	
	
	
		public ShootingMethod() {
			super();
			require(funnel.getInserter());
		}
		
	
	
}
