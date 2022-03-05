package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import org.greenblitz.motion.pid.PIDObject;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.kp;

public class MoveRailToPosition extends ClimbCommand {

	public enum State {
		PULL_UP, TRAVERSE, MID_GAME, HANGAR
	}

	private double goal;
	private boolean atLocation;

	public MoveRailToPosition(double goalPosition) {
		goal = goalPosition;

	}

	public MoveRailToPosition(State state) {
		switch (state){
			case PULL_UP:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR;
				return;
			case TRAVERSE:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_TRAVERSAL;
				return;
			case MID_GAME:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_MID_GAME;
				return;
			case HANGAR:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_HANGAR_ZONE;
				return;
		}
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		climb.safeMoveRailMotor(kp * (climb.getLoc() - goal));
		atLocation = Math.abs(climb.getLoc() - goal) < RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON;
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveRailMotor(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	public boolean isAtLocation() {
		return atLocation;
	}
}
