package edu.greenblitz.pegasus.commands.swerve.garbage;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public  class CalibrateMaxMin extends GBCommand {
	private final double power;
	private double maxVal;
	private double minVal;
	private final SwerveChassis.Module module;
	
	private SwerveChassis swerve = SwerveChassis.getInstance();

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
		SmartDashboard.putNumber(module.toString()+" min ",minVal);
		SmartDashboard.putNumber(module.toString()+" max ",maxVal);
		swerve.rotateModuleByPower(module, 0);
	}
}