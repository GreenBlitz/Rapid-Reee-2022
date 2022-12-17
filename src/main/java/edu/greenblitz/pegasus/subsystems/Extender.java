package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Extender extends GBSubsystem{
	private static Extender instance;
	private final DoubleSolenoid solenoid;

	public static Extender getInstance() {
		if (instance == null) {
			instance = new Extender();
		}
		return instance;
	}

	private Extender() {
		solenoid = new DoubleSolenoid(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID, RobotMap.Pegasus.Pneumatics.PCM.PCM_TYPE, RobotMap.Pegasus.Intake.Solenoid.FORWARD_PORT, RobotMap.Pegasus.Intake.Solenoid.REVERSE_PORT);

	}
	private void setValue(DoubleSolenoid.Value value) {
			solenoid.set(value);
		}
	public void extend() {
			setValue(DoubleSolenoid.Value.kForward);
		}

	public void retract() {
			setValue(DoubleSolenoid.Value.kReverse);
		}


		@Override
	public void periodic() {
			super.periodic();
		}
}

