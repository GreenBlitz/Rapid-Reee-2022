package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;

public class CalibrateMaxMin extends SwerveCommand {
	private final double power;
	private double maxVal;
	private double minVal;
	private final SwerveChassis.Module module;

	public CalibrateMaxMin(double power, SwerveChassis.Module module) {
		this.power = power;
		this.module = module;
	}

	@Override
	public void initialize() {
		maxVal = swerve.getRawAbsoluteAngle(module);
		minVal = swerve.getRawAbsoluteAngle(module);
	}

	@Override
	public void execute() {
		swerve.rotateModuleByPower(module, power);
		maxVal = Math.max(maxVal, swerve.getRawAbsoluteAngle(module));
		minVal = Math.min(minVal, swerve.getRawAbsoluteAngle(module));
	}

	@Override
	public void end(boolean interrupted) {
		swerve.rotateModuleByPower(module, 0);
	}
}
