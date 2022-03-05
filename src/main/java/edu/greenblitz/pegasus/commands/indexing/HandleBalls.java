package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.wpi.first.wpilibj2.command.Command;

public class HandleBalls extends IndexingCommand{
	private Command lastCommand;

	@Override
	public void initialize() {
		lastCommand = null;
	}

	@Override
	public void execute() {
		//if(indexing.getAllianceColor() == Indexing.BallColor.RED) System.out.println("Red");
		//else System.out.println("Blue");
		System.out.println("------------------");
		if(lastCommand == null || lastCommand.isFinished()) { //need to do an action
			System.out.println("Action needed");
			if (indexing.getBallCount() >= 2){
				System.out.println("Disabling Robot");
//				lastCommand = new DisableRoller();
				lastCommand.schedule();
				return;
			}
			if (indexing.getPerceivedColor() == indexing.getAllianceColor()) { // same color
				System.out.println("Adding a ball");
				indexing.addBall();
				if (!indexing.isBallUp()) { //first ball
					System.out.println("Moving until click");
					lastCommand = new MoveBallUntilClick();
					lastCommand.schedule();
				}else{
					//No need for action
				}
			} else if(indexing.getPerceivedColor() != Indexing.BallColor.OTHER){ // other color
				System.out.println("Trying to eject");
				if (!indexing.isBallUp()) {
					System.out.println("Shooter");
					lastCommand = new EjectEnemyBallFromShooter();
				}else{
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
}