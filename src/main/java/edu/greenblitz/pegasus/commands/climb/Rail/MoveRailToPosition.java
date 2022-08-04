package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbState;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.ff;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.kp;

public class MoveRailToPosition extends RailCommand {

	private double goal;
	private boolean atLocation;
	private ClimbState state;

	public MoveRailToPosition(double goalPosition) {
		super();
		goal = goalPosition;
	}

	public MoveRailToPosition(ClimbState state) {
		super();
		setState(state);
	}

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		climb.safeMoveRailMotor(kp * (goal - climb.getLoc()) + ff * Math.signum(goal - climb.getLoc()));
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveRailMotor(0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(goal - climb.getLoc()) < RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON;
	}

	public ClimbState getState() {
		return state;
	}

	public void setState(ClimbState state) {
		this.state = state;
		switch (state) {
			case PULL_UP:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR;
				break;
			case TRAVERSE:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_TRAVERSAL;
				break;
			case MID_GAME:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_MID_GAME;
				break;
			case START:
				goal = RobotMap.Pegasus.Climb.ClimbMotors.START_LOCATION;
				break;
		}
	}
}
