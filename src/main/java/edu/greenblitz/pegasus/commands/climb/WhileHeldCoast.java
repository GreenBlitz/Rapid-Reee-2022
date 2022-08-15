package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public class WhileHeldCoast extends GBCommand {
	@Override
	public void initialize() {
		super.initialize();
		RobotContainer.getInstance().getClimb().setTurningMotorIdle(AbstractMotor.IdleMode.Coast);

	}

	@Override
	public void end(boolean interrupted) {
		RobotContainer.getInstance().getClimb().setTurningMotorIdle(AbstractMotor.IdleMode.Brake);
	}
}
