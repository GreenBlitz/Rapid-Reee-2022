package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.pegasus.commands.swerve.AngPIDSupplier;
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
				SmartDashboard.putBoolean("has target",Limelight.getInstance().FindTarget());
				//SmartDashboard.putNumber("get curr x",SwerveChassis.getInstance().getCurSpeed().vxMetersPerSecond);
				//SmartDashboard.putNumber("get curr y",SwerveChassis.getInstance().getCurSpeed().vyMetersPerSecond);
				//SmartDashboard.putNumber("error",Limelight.getInstance().targetPos().getY() - SwerveChassis.getInstance().getChassisAngle());
				//SmartDashboard.putNumber("dx", Limelight.getInstance().targetPos().getX());
				//SmartDashboard.putNumber("dy", Limelight.getInstance().targetPos().getY());
				//SmartDashboard.putBoolean("target",Limelight.getInstance().FindTarget());
				SmartDashboard.putString("alliance", DriverStation.getAlliance().toString());
		
	}
}
