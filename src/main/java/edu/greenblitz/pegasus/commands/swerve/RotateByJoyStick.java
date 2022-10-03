package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.RobotMap;

public class RotateByJoyStick {
	private RobotMap.Pegasus.Joystick joystick;
	
	enum Direction {
		NORMAL(1),
		REVERSED(-1);
		
		private final int value;
		
		Direction(final int isReversed) {
			this.value = isReversed;
		}
		
		public int getValue() {
			return this.value;
		}
	}
	
	Direction isReversed = Direction.NORMAL;
	//...
	//	motor.setDirection(isReversed.getValue());
	
}
