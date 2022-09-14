package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.pegasus.subsystems.Climb;

public class WhileHeldCoast extends GBCommand {
	@Override
	public void initialize() {
		super.initialize();
		Climb.getInstance().setTurningMotorIdle(AbstractMotor.IdleMode.Coast);

	}

	@Override
	public void end(boolean interrupted) {
		Climb.getInstance().setTurningMotorIdle(AbstractMotor.IdleMode.Brake);
	}
}
