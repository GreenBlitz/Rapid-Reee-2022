package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbState;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.ff;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.kp;

public class RailToSecondBar extends RailCommand{
	public static final double GOAL = 0.0;
	public static final double Kp = 0.1;
	public static final double P_THRESHOLD = 0.1;

	@Override
	public void initialize() {

	}

	@Override
	public void execute() {
		double error = GOAL - climb.getLoc();
		if (Math.abs(error) < P_THRESHOLD) {
			climb.safeMoveRailMotor(error * Kp);
		} else {
			climb.safeMoveRailMotor(-1);
		}
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveRailMotor(0);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(GOAL - climb.getLoc()) < RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON;
	}
}
