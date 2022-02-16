package edu.greenblitz.pegasus.commands.complexClimb;

import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class HookMotorByPID extends ComplexClimbCommand {
	private PIDController controller;

	public HookMotorByPID(PIDObject obj) {
		controller = new PIDController(obj);
	}

	@Override
	public void execute() {
		complexClimb.safeMoveHookMotor(controller.calculatePID(-1)); // TODO: Check for PID value
	}

	@Override
	public void end(boolean interrupted) {
		complexClimb.safeMoveHookMotor(0);
	}
}
