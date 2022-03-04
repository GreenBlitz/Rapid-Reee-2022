package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;
import org.greenblitz.motion.pid.PIDObject;

public class MoveRailToPosition extends ClimbCommand {

	public enum State {
		PULL_UP, TRAVERSE
	}

	private static final double TICKS_PER_ANGLE = RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_TICKS_PER_METER;
	private static final double POWER = RobotMap.Pegasus.Climb.ClimbConstants.Rail.RAIL_POWER;
	private static final double TICKS_GOAL_FORWARD = RobotMap.Pegasus.Climb.ClimbConstants.Rail.TICKS_GOAL_FORWARD;
	private static final double TICKS_GOAL_BACKWARD = RobotMap.Pegasus.Climb.ClimbConstants.Rail.TICKS_GOAL_BACKWARD;
	private static final double EPSILON = RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON;

	private double goal;
	private State currentState;
	private static PIDObject pidObject;

	public MoveRailToPosition(double goalPositionInCM, State state) {
		this.goal = goalPositionInCM;
		switch (state) {
			case PULL_UP:
				pidObject = new PIDObject(RobotMap.Pegasus.Climb.ClimbConstants.Rail.kp1, RobotMap.Pegasus.Climb.ClimbConstants.Rail.ki1, 0, RobotMap.Pegasus.Climb.ClimbConstants.Rail.ff1);
				break;

			case TRAVERSE:
				pidObject = new PIDObject(RobotMap.Pegasus.Climb.ClimbConstants.Rail.kp2, RobotMap.Pegasus.Climb.ClimbConstants.Rail.ki2, 0, RobotMap.Pegasus.Climb.ClimbConstants.Rail.ff2);
				break;
		}
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveRailMotor(0);
	}

	@Override
	public boolean isFinished() {
		double currentPosition = climb.getRailMotorTicks() * TICKS_PER_ANGLE;

		double delta = goal - currentPosition;

		return (delta) < EPSILON;
	}

}
