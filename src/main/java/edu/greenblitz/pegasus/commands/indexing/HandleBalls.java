package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.pegasus.commands.intake.roller.DisableRoller;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.wpi.first.wpilibj2.command.Command;

public class HandleBalls extends IndexingCommand{
	private Command lastCommand;

	@Override
	public void execute() {
		if(lastCommand == null || lastCommand.isFinished()) { //need to do an action
			if (indexing.getBallCount() >= 2){
				lastCommand = new DisableRoller();
				lastCommand.schedule();
				return;
			}
			if (indexing.getPerceivedColor() == indexing.getAllianceColor()) { // same color
				indexing.addBall();
				if (!indexing.isBallUp()) { //first ball
					lastCommand = new MoveBallUntilClick();
					lastCommand.schedule();
				}else{
					//No need for action
				}
			} else { // other color
				if (!indexing.isBallUp()) {
					lastCommand = new EjectEnemyBallFromShooter();
				}else{
					lastCommand = new EjectEnemyBallFromGripper();
				}
				lastCommand.schedule();
			}
		}
	}
}
