package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.commands.BallQueue.WhileFirstBall;
import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.ExtendAndCollect;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.subsystems.BallQueue;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

import java.util.PriorityQueue;

public class RollByBallQueue extends ParallelCommandGroup {
	private double rollerPower;
	private double funnelPower;
	private static final double BALL_TIME = 0;
	private BallQueue.AllianceColor lastColor = BallQueue.AllianceColor.OTHER;
	private boolean isClicked = false;

	RollByBallQueue(double rollerPower, double funnelPower){
		this.funnelPower = funnelPower;
		this.rollerPower = rollerPower;
		addCommands(new ExtendAndCollect(rollerPower));
		addCommands(new ScheduleCommand(new ParallelRaceGroup(new WhileFirstBall(), new InsertByConstants(funnelPower))));

		BallQueue.getInstance().ballQueue = new PriorityQueue<>();
	}

	@Override
	public void execute() {
		if (lastColor != BallQueue.getInstance().getPerceivedColor()){
			BallQueue.getInstance().ballQueue.add(BallQueue.getInstance().getPerceivedColor());
		}
		lastColor = BallQueue.getInstance().getPerceivedColor();
		if(!BallQueue.getInstance().isBallUp ()){
			if (isClicked){
				BallQueue.getInstance().ballQueue.poll();
			}
			new ParallelRaceGroup(new WhileFirstBall(), new InsertByConstants(funnelPower)).schedule();
		}

	}
}
