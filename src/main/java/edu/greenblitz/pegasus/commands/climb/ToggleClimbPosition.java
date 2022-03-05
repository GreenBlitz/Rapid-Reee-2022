package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ToggleClimbPosition extends ParallelCommandGroup {
	
	JoystickButton toggleButton;
	MoveRailToPosition extend;
	MoveTurningToAngle turning;
	
	public ToggleClimbPosition(ClimbState state, JoystickButton button) {
		turning = new MoveTurningToAngle(state);
		extend = new MoveRailToPosition(state);
		addCommands(extend, turning);
		toggleButton = button;
	}
	
	@Override
	public void execute() {
		if (toggleButton.get()) {
			ClimbState state;
			if (extend.getState() != ClimbState.HANGAR) {
				state = ClimbState.HANGAR;
			} else {
				state = ClimbState.MID_GAME;
			}
			extend.setState(state);
			turning.setState(state);
		}
		super.execute();
	}
}
