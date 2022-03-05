package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class MoveBallUntilClick extends ParallelCommandGroup {
	private Indexing indexing;

	public MoveBallUntilClick(){
		addCommands(new RunFunnel(), new RunRoller());
		this.indexing = Indexing.getInstance();
	}

	@Override
	public void execute() {
		if (!indexing.isBallUp()) {
			super.execute();
		}
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
	}

	@Override
	public boolean isFinished() {
		return indexing.isBallUp();
	}
}
