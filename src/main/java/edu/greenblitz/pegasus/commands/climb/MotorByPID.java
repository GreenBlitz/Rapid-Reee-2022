package edu.greenblitz.pegasus.commands.climb;

import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MotorByPID extends ClimbCommand {

	private PIDController controller;

	public MotorByPID(PIDObject obj) {
		controller = new PIDController(obj);
	}

	@Override
	public void execute() {
		climb.safeMoveMotor(controller.calculatePID(-1));//TODO:check for PID value
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveMotor(0);
	}
}
