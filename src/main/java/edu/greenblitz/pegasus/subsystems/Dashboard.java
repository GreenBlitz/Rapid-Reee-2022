package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
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
		double brFalconEncoder =SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT);
		double blFlconEncoder =SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT);
		double flFalconEncoder =SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT);
		double frFalconEncoder =SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT);
		double brAbsEncoder = SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.BACK_RIGHT) * 2 * Math.PI;
		double blAbsEncoder = SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.BACK_LEFT) * 2 * Math.PI;
		double frAbsEncoder = SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.FRONT_RIGHT) * 2 * Math.PI;
		double flAbsEncoder = SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.FRONT_LEFT) * 2 * Math.PI;

		SmartDashboard.putNumber("br falcon", brFalconEncoder);
		SmartDashboard.putNumber("bl falcon", blFlconEncoder);
		SmartDashboard.putNumber("fr falcon", frFalconEncoder);
		SmartDashboard.putNumber("fl falcon", flFalconEncoder);

		SmartDashboard.putNumber("br abs encoder", brAbsEncoder);
		SmartDashboard.putNumber("bl abs encoder", blAbsEncoder);
		SmartDashboard.putNumber("fr abs encoder", frAbsEncoder);
		SmartDashboard.putNumber("fl abs encoder", flAbsEncoder);
//		SmartDashboard.putNumber("error", Math.toDegrees(falconEncoder - brAbsEncoder) %);
	}
}
