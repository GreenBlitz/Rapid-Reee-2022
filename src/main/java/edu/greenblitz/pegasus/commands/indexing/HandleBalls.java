package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.pegasus.commands.funnel.StopFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.StopRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// Would you plz handle ma ballz?? A.T.
public class HandleBalls extends IndexingCommand{
	private Command lastCommand;
	private boolean lastMacroSwitchPos;

	@Override
	public void initialize() {
		lastCommand = null;
		lastMacroSwitchPos = indexing.isBallUp();
	}

	@Override
	public void execute() {
		//if(indexing.getAllianceColor() == Indexing.BallColor.RED) System.out.println("Red");
		//else System.out.println("Blue");
		if (lastMacroSwitchPos && !indexing.isBallUp()){
			indexing.removeBall();
		}
		lastMacroSwitchPos = indexing.isBallUp();
		if(lastCommand == null || lastCommand.isFinished())
		{ //need to do an action
			System.out.println("Action needed");
			if (indexing.getBallCount() >= 2)
			{
				System.out.println("Disabling Robot");
				lastCommand = new StopRoller();
				lastCommand.schedule();
			}
			else if (indexing.getPerceivedColor() == indexing.getAllianceColor())
			{ // same color
				System.out.println("Adding a ball");
				indexing.addBall();
				lastCommand = new MoveBallUntilClick();
				lastCommand.schedule();
			}
			else if(indexing.getPerceivedColor() != Indexing.BallColor.OTHER)
			{ // other color
				System.out.println("Trying to eject");
				if (!indexing.isBallUp())
				{
					System.out.println("Shooter");
					indexing.addBall();
					lastCommand = new EjectEnemyBallFromShooter();
				}
				else
				{
					System.out.println("Gripper");
					lastCommand = new EjectEnemyBallFromGripper();
				}
				lastCommand.schedule();
			}
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		CommandScheduler.getInstance().schedule(new StopRoller(), new StopFunnel());
	}
}
