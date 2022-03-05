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
		if(lastCommand == null || lastCommand.isFinished()) { //need to do an action - not action is active right now
			System.out.println("Action needed");
			if (indexing.getPerceivedColor() == indexing.getAllianceColor()) { //the incoming ball is the same color
				System.out.println("Adding a ball");
				indexing.addBall();
				if (!indexing.isBallUp()) { //the incoming ball is the first ball
					System.out.println("Moving until click");
					lastCommand = new MoveBallUntilClick();
					lastCommand.schedule();
				}else{
					//No need for action - the incoming ball is the second ball
				}
			} else if(indexing.getPerceivedColor() != Indexing.BallColor.OTHER){ //the incoming ball is the second ball
				System.out.println("Trying to eject");
				if (!indexing.isBallUp()) { // there are no balls in the system - shoot by shooter
					System.out.println("Shooter");
					lastCommand = new EjectEnemyBallFromShooter();
				}else{ //there is one ball in the system - shoot by gripper
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