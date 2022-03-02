package edu.greenblitz.pegasus.commands.complexClimb;

import org.greenblitz.motion.pid.PIDController;
import org.greenblitz.motion.pid.PIDObject;

public class TurningMotorByPID extends ComplexClimbCommand {
	private PIDController controller;

	public TurningMotorByPID(PIDObject obj) {
		controller = new PIDController(obj);
	}

	@Override
	public void execute() {
		complexClimb.safeMoveTurningMotor(controller.calculatePID(-1)); // TODO: Check for PID value
	}

	@Override
	public void end(boolean interrupted) {
		complexClimb.safeMoveTurningMotor(0);
	}
}
