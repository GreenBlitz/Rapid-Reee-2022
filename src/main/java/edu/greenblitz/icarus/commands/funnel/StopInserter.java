package edu.greenblitz.icarus.commands.funnel;

public class StopInserter extends ShootingMethod {
	
		
		@Override
		public void initialize() {
			funnel.moveInserter(0);
		}
		
		@Override
		public boolean isFinished() {
			return true;
		}
	
	
}
