package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;

public class MoveRail extends ClimbCommand {

	private static final double POWER = RobotMap.Pegasus.Climb.ClimbConstants.Rail.ROTATION_POWER;
	private static final double TICKS_GOAL_FORWARD = RobotMap.Pegasus.Climb.ClimbConstants.Rail.TICKS_GOAL_FORWARD;
	private static final double TICKS_GOAL_BACKWARD = RobotMap.Pegasus.Climb.ClimbConstants.Rail.TICKS_GOAL_BACKWARD;
	private static final double EPSILON = RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON;
	private boolean isBackwards;

	public MoveRail(boolean isBackwards) {
		this.isBackwards = isBackwards;
	}

	@Override
	public void execute() {
		if (isBackwards) {
			climb.moveRailMotor(-POWER);
		} else {
			climb.moveRailMotor(POWER);
		}
	}

	@Override
	public void end(boolean interrupted) {
		climb.moveRailMotor(0);
	}

	@Override
	public boolean isFinished() {
		if (isBackwards) {
			return (climb.getRailMotorTicks() - TICKS_GOAL_BACKWARD) < EPSILON;
		}
		return (climb.getRailMotorTicks() - TICKS_GOAL_FORWARD) < EPSILON;
	}

	public boolean isBackwards() {
		return isBackwards;
	}

	public void toggleIsBackwards() {
		isBackwards = !isBackwards;
	}
}
