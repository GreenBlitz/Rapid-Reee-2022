package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Intake {
	private static Intake instance;
	private final Roller roller;
	private final Extender extender;

	protected Intake() {
		roller = new Intake.Roller();
		extender = new Intake.Extender();
	}

	private static void init() {
		instance = new Intake();
		CommandScheduler.getInstance().registerSubsystem(instance.roller);
		CommandScheduler.getInstance().registerSubsystem(instance.extender);
	}

	public static Intake getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public void initDefaultCommand(SmartJoystick joystick) {
		instance.getRoller().setDefaultCommand(new HandleBalls());
//		Funnel.getInstance().setDefaultCommand(new RunFunnel());
	}

	public void moveRoller(double power) {
		roller.rollerMotor.set(power);
	}

	public void stopRoller() {
		moveRoller(0.0);
	}

	public void moveRoller(boolean reversed) {
		moveRoller(reversed ? RobotMap.Pegasus.Intake.REVERSE_POWER : RobotMap.Pegasus.Intake.POWER);
	}

	public void moveRoller() {
		moveRoller(false);
	}

	public void extend() {
		extender.extender.set(DoubleSolenoid.Value.kForward);
	}

	public void retract() {
		extender.extender.set(DoubleSolenoid.Value.kReverse);
	}

	public boolean isExtended() {
		return extender.extender.get().equals(DoubleSolenoid.Value.kForward);
	}

	public void toggleExtender() {
		System.out.println(isExtended());
		if (isExtended()) {
			retract();
		} else {
			extend();
		}
	}

	public Roller getRoller() {
		return roller;
	}

	public Extender getExtender() {
		return extender;
	}

	private class IntakeSubsystem extends GBSubsystem {
		public Intake getIntake() {
			return Intake.this;
		}
	}

	private class Roller extends IntakeSubsystem {

		private final WPI_TalonSRX rollerMotor;

		private Roller() {
			rollerMotor = new WPI_TalonSRX(RobotMap.Pegasus.Intake.Motors.ROLLER_PORT);
			rollerMotor.setInverted(RobotMap.Pegasus.Intake.Motors.IS_REVERSED);
			rollerMotor.setNeutralMode(NeutralMode.Coast);
		}

		@Override
		public void periodic() {
		}
	}

	public class Extender extends IntakeSubsystem {

		private final DoubleSolenoid extender;

		private Extender() {
			extender = new DoubleSolenoid(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID, RobotMap.Pegasus.Pneumatics.PCM.PCM_TYPE, RobotMap.Pegasus.Intake.Solenoid.FORWARD_PORT, RobotMap.Pegasus.Intake.Solenoid.REVERSE_PORT);
		}

		private void setValue(DoubleSolenoid.Value value) {
			extender.set(value);
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
}
