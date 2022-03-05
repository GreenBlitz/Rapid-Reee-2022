package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;

public class MoveTurning extends ClimbCommand {

	private static final double POWER = RobotMap.Pegasus.Climb.ClimbConstants.Hook.ROTATION_POWER;
	private static final double TICKS_GOAL_FORWARD = RobotMap.Pegasus.Climb.ClimbConstants.Hook.TICKS_GOAL_FORWARD;
	private static final double TICKS_GOAL_BACKWARD = RobotMap.Pegasus.Climb.ClimbConstants.Hook.TICKS_GOAL_BACKWARD;
	private static final double EPSILON = RobotMap.Pegasus.Climb.ClimbConstants.Hook.EPSILON;
	private boolean isBackwards;

	public MoveTurning(boolean isBackwards) {
		this.isBackwards = isBackwards;
	}

	@Override
	public void execute() {
		if (isBackwards) {
			climb.moveTurningMotor(-POWER);
		} else {
			climb.moveTurningMotor(POWER);
		}
	}

	@Override
	public void end(boolean interrupted) {
		climb.moveTurningMotor(0);
	}

	@Override
	public boolean isFinished() {
		if (isBackwards) {
			return (climb.getTurningMotorTicks() - TICKS_GOAL_BACKWARD) < EPSILON;
		}
		return (climb.getTurningMotorTicks() - TICKS_GOAL_FORWARD) < EPSILON;
	}

	public boolean isBackwards() {
		return isBackwards;
	}

	public void toggleIsBackwards() {
		isBackwards = !isBackwards;
	}
}
