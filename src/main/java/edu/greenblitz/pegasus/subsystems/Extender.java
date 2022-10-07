//package edu.greenblitz.pegasus.subsystems;
//
//
//import edu.greenblitz.pegasus.RobotMap;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
//
//public class Extender extends Intake {
//
//	private final DoubleSolenoid extender;
//
//	private Extender() {
//		extender = new DoubleSolenoid(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID, RobotMap.Pegasus.Pneumatics.PCM.PCM_TYPE, RobotMap.Pegasus.Intake.Solenoid.FORWARD_PORT, RobotMap.Pegasus.Intake.Solenoid.REVERSE_PORT);
//	}
//
//	private static Extender instance;
//
//	public static Extender getInstance() {
//		if (instance == null) {
//			instance = new Extender();
//		}
//		return instance;
//	}
//
//	private void setValue(DoubleSolenoid.Value value) {
//		extender.set(value);
//	}
//
//	public void extend() {
//		setValue(DoubleSolenoid.Value.kForward);
//	}
//
//	public void retract() {
//		setValue(DoubleSolenoid.Value.kReverse);
//	}
//
//
//	public boolean isExtended() {
//		return extender.get().equals(DoubleSolenoid.Value.kForward);
//	}
//
//	@Override
//	public void periodic() {
//		super.periodic();
//	}
//}