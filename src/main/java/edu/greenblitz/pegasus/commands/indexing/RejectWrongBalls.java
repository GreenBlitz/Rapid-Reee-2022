package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RejectWrongBalls extends IndexingCommand {
	Indexing.BallColor robotColor;

	public static final double ACTIVATION_TIME = 3.0;

	@Override
	public void initialize() {
		this.robotColor = indexing.getPerceivedColor();
}

	@Override
	public void execute() {
		if (robotColor == Indexing.BallColor.OTHER) {
			this.robotColor = indexing.getPerceivedColor();
		}
		if (indexing.getPerceivedColor() != robotColor)
			new ParallelRaceGroup(
					new WaitCommand(ACTIVATION_TIME),
					new ReverseRunFunnel(),
					new ReverseRunRoller()
			);
	}
}
