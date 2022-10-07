//package edu.greenblitz.pegasus.subsystems;
//
//import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
//import edu.greenblitz.pegasus.RobotMap;
//import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
//import edu.wpi.first.wpilibj2.command.CommandScheduler;
//
//public class Intake extends GBSubsystem {
//	private final Roller roller;
//	private final Extender extender;
//
//	protected Intake() {
//		roller = Roller.getInstance();
//		extender = Extender.getInstance();
//		CommandScheduler.getInstance().registerSubsystem(roller);
//		CommandScheduler.getInstance().registerSubsystem(extender);
//
//	}
//
//	private static Intake instance;
//
//	public static Intake getInstance() {
//		if (instance == null) {
//			instance = new Intake();
//		}
//		return instance;
//	}
//
//	public void initDefaultCommand() {
//		Roller.getInstance().setDefaultCommand(new HandleBalls());
//	}
//
//	public void moveRoller(double power) {
//		Roller.getInstance().setPower(power);
//	}
//
//	public void stopRoller() {
//		moveRoller(0.0);
//	}
//
//	public void moveRoller(boolean reversed) {
//		moveRoller(reversed ? RobotMap.Pegasus.Intake.REVERSE_POWER : RobotMap.Pegasus.Intake.POWER);
//	}
//
//	public void moveRoller() {
//		moveRoller(false);
//	}
//
//	public void extend() {
//		Extender.getInstance().extend();
//	}
//
//	public void retract() {
//		Extender.getInstance().retract();
//	}
//
//	public boolean isExtended() {
//		return Extender.getInstance().isExtended();
//	}
//
//	public void toggleExtender() {
//		System.out.println(isExtended());
//		if (isExtended()) {
//			retract();
//		} else {
//			extend();
//		}
//	}
//
//	public Roller getRoller() {
//		return roller;
//	}
//
//
//	public Extender getExtender() {
//		return extender;
//	}
//
//
//
//}
