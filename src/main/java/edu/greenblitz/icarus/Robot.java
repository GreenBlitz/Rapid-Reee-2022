package edu.greenblitz.icarus;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {

	@Override
	public void robotInit() {
		OI.getInstance();
		CommandScheduler.getInstance().enable();
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		if (RobotController.getBatteryVoltage() < 11) {
			System.err.println("BATTERY LEVELS DANGEROUSLY LOW");
		}
	}

	@Override
	public void teleopInit(){    }
}
