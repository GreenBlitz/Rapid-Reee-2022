package edu.greenblitz.icarus.commands.funnel;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.icarus.RobotMap;
import edu.greenblitz.icarus.commands.shooter.ShooterCommand;

public class InsertByConstants extends {
		private double power;
		
		public InsertByConstant(double power) {
			super();
			this.power = power;
		}
		
		@Override
		public void execute() {
			funnel.moveInserter(power);
		}
		
		@Override
		public void end(boolean interrupted) {
			funnel.moveInserter(0);
		}
		
		@Override
		public boolean isFinished() {
			return false;
		}

}
