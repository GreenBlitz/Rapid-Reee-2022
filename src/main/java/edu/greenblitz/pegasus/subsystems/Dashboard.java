package edu.greenblitz.pegasus.subsystems;

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
				SmartDashboard.putBoolean("macroSwitch", DigitalInputMap.getInstance().getValue(0));
				SmartDashboard.putBoolean("readyToShoot", Shooter.getInstance().isPreparedToShoot());
				SmartDashboard.putNumber("ShooterSpeed", Shooter.getInstance().getShooterSpeed());

				SmartDashboard.putNumber("FR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_RIGHT)), 360));
				SmartDashboard.putNumber("FL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.FRONT_LEFT)), 360));
				SmartDashboard.putNumber("BR-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_RIGHT)), 360));
				SmartDashboard.putNumber("BL-angle-neo", GBMath.modulo(Math.toDegrees(SwerveChassis.getInstance().getModuleAngle(SwerveChassis.Module.BACK_LEFT)), 360));
		
			SmartDashboard.putNumber("FR-lamprey-volt", SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_RIGHT));
			SmartDashboard.putNumber("FL-lamprey-volt", SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_LEFT));
			SmartDashboard.putNumber("BR-lamprey-volt", SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_RIGHT));
			SmartDashboard.putNumber("BL-lamprey-volt", SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_LEFT));
		
		SmartDashboard.putNumber("FR-lamprey", Calibration.FRONT_RIGHT.linearlyInterpolate( SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_RIGHT))[0]);
		SmartDashboard.putNumber("FL-lamprey", Calibration.FRONT_LEFT.linearlyInterpolate( SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.FRONT_LEFT))[0]);
		SmartDashboard.putNumber("BR-lamprey", Calibration.BACK_RIGHT.linearlyInterpolate( SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_RIGHT))[0]);
		SmartDashboard.putNumber("BL-lamprey", Calibration.BACK_LEFT.linearlyInterpolate( SwerveChassis.getInstance().getModuleLampreyVoltage(SwerveChassis.Module.BACK_LEFT))[0]);
		
		
		
		
		
		
		SmartDashboard.putString("alliance", DriverStation.getAlliance().toString());
		
	}
}
