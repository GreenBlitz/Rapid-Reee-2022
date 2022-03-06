package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbState;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.ff;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.kp;

public class MoveTurningToAngle extends TurningCommand {

	private double goal;
	private boolean atAngle;
	private ClimbState state;
	public MoveTurningToAngle(double goalAngle) {
		super();
		goal = goalAngle;

	}

	public MoveTurningToAngle(ClimbState state) {
		setState(state);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		climb.safeMoveTurningMotor(kp * (goal - climb.getAng()) + ff * Math.signum(goal - climb.getAng()));
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveTurningMotor(0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(goal - climb.getAng()) < RobotMap.Pegasus.Climb.ClimbConstants.Rotation.EPSILON;
	}

	
	public ClimbState getState(){
		return state;
	}
	
	public void setState(ClimbState state) {
		this.state = state;
		switch (state){
			case PULL_UP:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_SECOND_BAR;
				return;
			case TRAVERSE:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_TRAVERSAL;
				return;
			case MID_GAME:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_MID_GAME;
				return;
			case HANGAR:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_HANGAR_ZONE;
				return;
		}
	}
}
