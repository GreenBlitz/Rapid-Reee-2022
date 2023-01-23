package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.GBMath;

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


	double angularState = 0;
	long lastRead = System.currentTimeMillis();
	@Override
	public void periodic() {
		SmartDashboard.putNumber("module speed", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
		SmartDashboard.putNumber("angular speed", (SwerveChassis.getInstance().getPigeonGyro().getYaw() - angularState)/((System.currentTimeMillis() - lastRead)/1000.0));
		SmartDashboard.putNumber("pigeon angle", SwerveChassis.getInstance().getPigeonGyro().getYaw());
		lastRead = System.currentTimeMillis();
		SmartDashboard.putNumber("FR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
		SmartDashboard.putNumber("FL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
		SmartDashboard.putNumber("BR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
		SmartDashboard.putNumber("BL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		double sum = 0;
		for (SwerveChassis.Module module : SwerveChassis.Module.values()){
			sum+=SwerveChassis.getInstance().getModuleAngle(module);
		}
		SmartDashboard.putBoolean("an azimuth encoder is nan", Double.isNaN(sum));


		SmartDashboard.putNumber("FR-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
		SmartDashboard.putNumber("FL-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
		SmartDashboard.putNumber("BR-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
		SmartDashboard.putNumber("BL-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		
		SmartDashboard.putNumber("FR-abs-encoder-volt", SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("FL-abs-encoder-volt", SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("BR-abs-encoder-volt", SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("BL-abs-encoder-volt", SwerveChassis.getInstance().getModuleAbsoluteValue(SwerveChassis.Module.BACK_LEFT));
		
		SmartDashboard.putString("alliance", DriverStation.getAlliance().toString());
	}
}
