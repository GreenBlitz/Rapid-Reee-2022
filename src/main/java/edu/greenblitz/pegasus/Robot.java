package edu.greenblitz.pegasus;

import edu.greenblitz.pegasus.subsystems.*;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.greenblitz.pegasus.utils.VisionMaster;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
	@Override
	public void robotInit() {
		CommandScheduler.getInstance().enable();

		DigitalInputMap.getInstance();

		//Pneumatics.init();
		Intake.getInstance();
		//Shifter.init();
		Funnel.getInstance();
		Shooter.init();
		ComplexClimb.getInstance();
		ColorSensor.getInstance();
		OI.getInstance();

//        VisionMaster.getInstance().register();
		Chassis.getInstance();
		// Must be last!

	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		VisionMaster.GameState.DISABLED.setAsCurrent();
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void teleopInit() {
		//Shifter.getInstance().setShift(Gear.SPEED);
		CommandScheduler.getInstance().cancelAll();
		//VisionMaster.GameState.TELEOP.setAsCurrent();
		//Chassis.getInstance().toBrake();
		//Chassis.getInstance().resetGyro();
		//Chassis.getInstance().resetEncoders();
		//new LocalizerCommandRunner().schedule();

		//VisionMaster.Algorithm.HEXAGON.setAsCurrent();
		//Shifter.getInstance().setShift(Gear.SPEED);
		//GlobalGearContainer.getInstance().setGear(Gear.SPEED);

		//new ResetDome(-0.3).schedule();
		//new ResetEncoderWhenInSide().schedule();
		//new ExtendRoller().schedule();
		//new StopShooter().schedule();

        /*
        if (!DriverStation.getInstance().isFMSAttached()){
            new CompressorOn().schedule();
            new ResetEncoderWhenInSide().schedule();
            new ClimbByTriggers(OI.getInstance().getMainJoystick(), OI.getInstance().getSideStick(), 0.4, 0.4).schedule();
            Localizer.getInstance().reset(Chassis.getInstance().getLeftMeters(), Chassis.getInstance().getRightMeters());
        }*/
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("RIGHT STICK X", OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X));
		//SmartDotring("THING", Chassis.getInstance().getCurrentCommand().toString());
		//Command shooterCommand = Shooter.getInstance().getCurrentCommand();
		//SmartDashboard.putString("Shooter::currentCommand", shooterCommand == null ? "" : shooterCommand.getName());
		//Command chassisCommand = Chassis.getInstance().getCurrentCommand();
		//SmartDashboard.putString("Chassis::currentCommand", chassisCommand == null ? "" : chassisCommand.getName());
	}



	@Override
	public void autonomousInit() {
		//Shifter.getInstance().setShift(Gear.SPEED);
		VisionMaster.GameState.AUTONOMOUS.setAsCurrent();
		VisionMaster.Algorithm.HEXAGON.setAsCurrent();
		// new ResetEncoderWhenInSide().initialize();
		//new Trench8BallAuto().schedule();
		//new FiveBallTrench().schedule();
		//new ThreeBallNoVision().schedule();
		//new FiveBallTrenchSteal().schedule();
	}


	@Override
	public void testInit() {
		CommandScheduler.getInstance().cancelAll();
	}

	@Override
	public void testPeriodic() {
		SmartDashboard.putNumber("RIGHT STICK X", OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X));
	}
}
