package edu.greenblitz.icarus;

import edu.greenblitz.icarus.subsystems.*;
import edu.greenblitz.icarus.utils.DigitalInputMap;
import edu.greenblitz.icarus.utils.VisionMaster;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.greenblitz.motion.Localizer;

import java.io.*;
import java.util.HashMap;

public class Robot extends TimedRobot {

	private double startTime;
	private boolean recordDriver = true;//will be false when we need to stop the recording.
	private HashMap<Double, HashMap<Integer, Double>> followDriverData = new HashMap<>();
	private final String dataPlace = "/home/lvuser/command_recordings/ourRecord";

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

//        UARTCommunication.getInstance().register();

		OI.getInstance();

//        VisionMaster.getInstance().register();
		//Chassis.init();
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
		System.out.println(followDriverData.toString());
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
		File f = new File(dataPlace);
		System.out.println(f.isFile());
	}

	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber("RIGHT STICK X", OI.getInstance().getMainJoystick().getAxisValue(SmartJoystick.Axis.RIGHT_X));
		//SmartDotring("THING", Chassis.getInstance().getCurrentCommand().toString());
		//Command shooterCommand = Shooter.getInstance().getCurrentCommand();
		//SmartDashboard.putString("Shooter::currentCommand", shooterCommand == null ? "" : shooterCommand.getName());
		//Command chassisCommand = Chassis.getInstance().getCurrentCommand();
		//SmartDashboard.putString("Chassis::currentCommand", chassisCommand == null ? "" : chassisCommand.getName());
		//follow driver code is here:
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
//		collectFollowDriverData(false);
	}

}
