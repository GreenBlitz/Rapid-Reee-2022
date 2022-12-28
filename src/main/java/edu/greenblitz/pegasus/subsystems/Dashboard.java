package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.swerve.AngPIDSupplier;
import edu.greenblitz.pegasus.subsystems.swerve.Calibration;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.OI;

import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Dashboard extends GBSubsystem {

	private static Dashboard instance;

	public static Dashboard init() {
		if (instance == null) {
			instance = new Dashboard();
		}
		return instance;
	}


	@Override
	public void periodic() {
		SmartDashboard.putNumber("FR lamprey voltage",SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("FL lamprey voltage",SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("BR lamprey voltage",SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("BL lamprey voltage",SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_LEFT));
		SmartDashboard.putNumber("FR lamprey angle", Math.toDegrees(Calibration.FRONT_RIGHT.linearlyInterpolate(SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_RIGHT))[0] * RobotMap.Pegasus.Swerve.NEO_PHYSICAL_TICKS_TO_RADIANS));
		SmartDashboard.putNumber("FL lamprey angle", Math.toDegrees(Calibration.FRONT_LEFT.linearlyInterpolate(SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_LEFT))[0] * RobotMap.Pegasus.Swerve.NEO_PHYSICAL_TICKS_TO_RADIANS));
		SmartDashboard.putNumber("BR lamprey angle", Math.toDegrees(Calibration.BACK_RIGHT.linearlyInterpolate(SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_RIGHT))[0] * RobotMap.Pegasus.Swerve.NEO_PHYSICAL_TICKS_TO_RADIANS));
		SmartDashboard.putNumber("BL lamprey angle", Math.toDegrees(Calibration.BACK_LEFT.linearlyInterpolate(SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_LEFT))[0] * RobotMap.Pegasus.Swerve.NEO_PHYSICAL_TICKS_TO_RADIANS));
	}
}
