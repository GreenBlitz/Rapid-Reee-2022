package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
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
				//lampreys
//				SmartDashboard.putNumber("FR-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
//				SmartDashboard.putNumber("FL-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
//				SmartDashboard.putNumber("BR-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
//				SmartDashboard.putNumber("BL-angle", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getLampreyAngle(SwerveChassis.Module.BACK_LEFT)), 360));
				//neos
				SmartDashboard.putNumber("FR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
				SmartDashboard.putNumber("FL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
				SmartDashboard.putNumber("BR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
				SmartDashboard.putNumber("BL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
				//pigeon
				SmartDashboard.putNumber("pigeon angle", Math.toDegrees(SwerveChassis.getInstance().getChassisAngle()));
				SmartDashboard.putNumber("pigeon offset", Math.toDegrees(SwerveChassis.getInstance().pigeonAngleOffset));
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
