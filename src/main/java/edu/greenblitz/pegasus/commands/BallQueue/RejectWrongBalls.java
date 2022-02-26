package edu.greenblitz.pegasus.commands.BallQueue;


import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.subsystems.BallQueue;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RejectWrongBalls extends BallQueueCommand {
	BallQueue.AllianceColor myColor;

	public final static double ROLLER_POWER = -0.8; //both should be negative
	static public final double FUNNEL_POWER = -0.8;
	final static public double ACTIVATION_TIME = 3.0;

	@Override
	public void initialize() {
		this.myColor = ballQueue.getPerceivedColor();
}

	@Override
	public void execute() {
		if (myColor == BallQueue.AllianceColor.OTHER) {
			this.myColor = ballQueue.getPerceivedColor();
		}
		if (ballQueue.getPerceivedColor() != myColor)
			new ParallelRaceGroup(
					new WaitCommand(ACTIVATION_TIME),
					new InsertByConstants(FUNNEL_POWER),
					new RollByConstant(ROLLER_POWER)
			);
	}
}
