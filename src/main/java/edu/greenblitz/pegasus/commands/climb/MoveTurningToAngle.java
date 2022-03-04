package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.pegasus.RobotMap;

public class MoveTurningToAngle extends ClimbCommand {

	private double rads;

	public MoveTurningToAngle(double rads) {
		super();
		this.rads = rads;
	}

	@Override
	public void initialize() {

	}
}
