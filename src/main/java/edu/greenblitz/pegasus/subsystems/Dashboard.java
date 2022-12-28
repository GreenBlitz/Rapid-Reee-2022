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


	double angularState = 0;
	long lastRead = System.currentTimeMillis();
	@Override
	public void periodic() {
		SmartDashboard.putNumber("module speed", SwerveChassis.getInstance().getModuleState(SwerveChassis.Module.FRONT_RIGHT).speedMetersPerSecond);
		SmartDashboard.putNumber("angular speed", (SwerveChassis.getInstance().getPigeonGyro().getYaw() - angularState)/((System.currentTimeMillis() - lastRead)/1000.0));
		lastRead = System.currentTimeMillis();
		angularState = SwerveChassis.getInstance().getPigeonGyro().getYaw();
	}
}
