package edu.greenblitz.pegasus.commands.swerve.garbage;

import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;

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
		maxVal = swerve.getRawLampreyAngle(module);
		minVal = swerve.getRawLampreyAngle(module);
	}

	@Override
	public void execute() {
		swerve.rotateModuleByPower(module, power);
		maxVal = Math.max(maxVal, swerve.getRawLampreyAngle(module));
		minVal = Math.min(minVal, swerve.getRawLampreyAngle(module));
	}

	@Override
	public void end(boolean interrupted) {
		swerve.rotateModuleByPower(module, 0);
	}
}
