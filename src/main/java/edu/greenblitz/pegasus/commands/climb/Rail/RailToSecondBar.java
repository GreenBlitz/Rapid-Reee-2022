package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbState;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.ff;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.kp;

public class RailToSecondBar extends RailCommand{
	public static final double GOAL = 0.0;
	
	@Override
	public void initialize() {
	
	}
	
	@Override
	public void execute() {
			climb.safeMoveRailMotor(-1);
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
