package edu.greenblitz.pegasus.commands.swerve.garbage;

import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;

public class MoveSingle extends SwerveCommand {

	private final double power;
	private final double targetAngle;
	private final SwerveChassis.Module module;

	public MoveSingle(SwerveChassis.Module module, double targetAngleInRotations, double linPower) {
		this.power = linPower;
		this.targetAngle = targetAngleInRotations;
		this.module = module;
	}

	@Override
	public void execute() {
		swerve.moveSingleModule(module, targetAngle, power);
	}

	@Override
	public void end(boolean interrupted) {
		swerve.moveSingleModule(module, targetAngle, 0);
	}
}
