package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbState;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.*;

public class HoldTurning extends TurningCommand {
	/**
	 * this command has been changed to be very specific it is advised to not use it in another place until a codee review is finished
	 *
	 *
	 *
	 *
	 *
	 *
	 */
	private double goal;
	private ClimbState state;
	public HoldTurning(double goalAngle) {
		super();
		goal = goalAngle;
		
	}
	
	public HoldTurning(ClimbState state) {
		super();
		setState(state);
	}
	
	@Override
	public void initialize() {
	}
	
	@Override
	public void execute() {
		climb.safeMoveTurningMotor(kp_static * (goal - climb.getAng()) + ff_static);
	}
	
	@Override
	public void end(boolean interrupted) {
		climb.safeMoveTurningMotor(0);
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
	
	
	public ClimbState getState(){
		return state;
	}
	
	public void setState(ClimbState state) {
		this.state = state;
		switch (state){
			case TRAVERSE:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_TRAVERSAL;
				return;
			case MID_GAME:
				goal = RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_MID_GAME;
				return;
			case START:
				goal = RobotMap.Pegasus.Climb.ClimbMotors.START_ANGLE;
				return;
		}
	}
}
