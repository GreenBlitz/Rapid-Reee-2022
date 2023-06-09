package edu.greenblitz.pegasus.commands.indexing;

import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromGripper;
import edu.greenblitz.pegasus.commands.multiSystem.EjectEnemyBallFromShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;

public class HandleBalls extends IndexingCommand{
	private Command runningCommand;

	@Override
	public void initialize() {
		runningCommand = null;
	}

	private boolean isCommandNeeded() {
		return runningCommand == null || runningCommand.isFinished();
	}

	private void insertFriendlyBall() {
		System.out.println("Adding a ball");
		indexing.addBall();

		if (!indexing.isBallUp()) {
			System.out.println("Moving until click");
			runningCommand = new MoveBallUntilClick();
			runningCommand.schedule(false);
		}
	}

	private void ejectEnemyBall() {
		System.out.println("Trying to eject");
		if (!indexing.isBallUp()) {
			System.out.println("Ejecting from shooter");
			runningCommand = new EjectEnemyBallFromShooter();
		} else {
			System.out.println("Ejecting from gripper");
			runningCommand = new EjectEnemyBallFromGripper();
		}
		runningCommand.schedule(false);
	}

	@Override
	public void execute() {
		if(isCommandNeeded()) {
			if (indexing.getPerceivedColor() == indexing.getAllianceColor()) {
				insertFriendlyBall();
			} else if (indexing.getPerceivedColor() != DriverStation.Alliance.Invalid){
				ejectEnemyBall();
			}
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}