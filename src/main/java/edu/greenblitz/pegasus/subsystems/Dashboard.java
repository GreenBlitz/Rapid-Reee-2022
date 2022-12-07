package edu.greenblitz.pegasus.subsystems;

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
		switch (OI.getInstance().getIOMode()) {
			case DEBUG:
				SmartDashboard.putNumber("FR targert", SwerveChassis.getInstance().getTarget(SwerveChassis.Module.FRONT_RIGHT));
				SmartDashboard.putNumber("FL targert", SwerveChassis.getInstance().getTarget(SwerveChassis.Module.FRONT_LEFT));
				SmartDashboard.putNumber("BR targert", SwerveChassis.getInstance().getTarget(SwerveChassis.Module.BACK_RIGHT));
				SmartDashboard.putNumber("BL targert", SwerveChassis.getInstance().getTarget(SwerveChassis.Module.BACK_LEFT));

				SmartDashboard.putNumber("FR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
				SmartDashboard.putNumber("FL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
				SmartDashboard.putNumber("FL-angle-lamprey", SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_LEFT));
				SmartDashboard.putNumber("FR-angle-lamprey", SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_RIGHT));
				SmartDashboard.putNumber("BR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
				SmartDashboard.putNumber("BL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));

				SmartDashboard.putNumber("FL-lin-velocity", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_LEFT).speedMetersPerSecond);
				SmartDashboard.putNumber("FR-lin-velocity", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
				SmartDashboard.putNumber("BL-lin-velocity", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.BACK_LEFT).speedMetersPerSecond);
				SmartDashboard.putNumber("BR-lin-velocity", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.BACK_RIGHT).speedMetersPerSecond);

				//pigeon
				SmartDashboard.putNumber("pigeon angle", Math.toDegrees(SwerveChassis.getInstance().getChassisAngle()));
				//pneumatics
				SmartDashboard.putNumber("pres",Pneumatics.getInstance().getPressure());
				SmartDashboard.putBoolean("enabled", Pneumatics.getInstance().isEnabled());
				//shooter
				SmartDashboard.putBoolean("macroSwitch", DigitalInputMap.getInstance().getValue(0));
				SmartDashboard.putBoolean("readyToShoot", Shooter.getInstance().isPreparedToShoot());
				SmartDashboard.putNumber("ShooterSpeed", Shooter.getInstance().getShooterSpeed());
				break;
//			case REAL:
//				SmartDashboard.putBoolean("macroSwitch", DigitalInputMap.getInstance().getValue(0));
//				break;
			case AMIR:
				SmartDashboard.putBoolean("macroSwitch", DigitalInputMap.getInstance().getValue(0));
				SmartDashboard.putBoolean("readyToShoot", Shooter.getInstance().isPreparedToShoot());
				SmartDashboard.putNumber("ShooterSpeed", Shooter.getInstance().getShooterSpeed());

				SmartDashboard.putNumber("FR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
				SmartDashboard.putNumber("FL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
				SmartDashboard.putNumber("BR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
				SmartDashboard.putNumber("BL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));

				SmartDashboard.putString("alliance", DriverStation.getAlliance().toString());
		}
	}
}
