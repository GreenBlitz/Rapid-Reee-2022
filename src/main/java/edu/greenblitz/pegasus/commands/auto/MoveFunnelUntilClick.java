package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.funnel.FunnelCommand;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.Intake;

public class MoveFunnelUntilClick extends FunnelCommand {
	private Indexing indexing;

	public MoveFunnelUntilClick(){
		this.indexing = Indexing.getInstance();
	}

	@Override
	public void execute() {
		funnel.moveMotor();
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stopMotor();
	}

	@Override
	public boolean isFinished() {
		return indexing.isBallUp();
	}
}