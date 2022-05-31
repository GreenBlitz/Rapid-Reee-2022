package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.gblib.base.GBCommand;

import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.Intake;

public class MoveBallUntilClick extends GBCommand {
	private Funnel funnel;
	private Intake intake;
	private Indexing indexing;

	public MoveBallUntilClick(){
		this.funnel = Funnel.getInstance();
		this.intake = Intake.getInstance();
		this.indexing = Indexing.getInstance();
	}

	@Override
	public void execute() {
		funnel.moveMotor();
		intake.moveRoller();
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stopMotor();
		intake.stopRoller();
	}

	@Override
	public boolean isFinished() {
		return indexing.isBallUp();
	}
}