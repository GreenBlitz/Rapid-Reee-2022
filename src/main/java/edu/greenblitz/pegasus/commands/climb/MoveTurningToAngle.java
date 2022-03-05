package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import org.greenblitz.motion.pid.PIDObject;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.kp;

public class MoveTurningToAngle extends ClimbCommand {

	public enum State {
		PULL_UP, TRAVERSE, MID_GAME, HANGAR
	}

	private double goal;
	private boolean atAngle;

	public MoveTurningToAngle(double goalAngle) {
		goal = goalAngle;

	}

	public MoveTurningToAngle(State state) {
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

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		climb.safeMoveTurningMotor(kp * (climb.getAng() - goal));
		atAngle = Math.abs(climb.getAng() - goal) < RobotMap.Pegasus.Climb.ClimbConstants.Rotation.EPSILON;
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveTurningMotor(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	public boolean isAtAngle() {
		return atAngle;
	}
}
