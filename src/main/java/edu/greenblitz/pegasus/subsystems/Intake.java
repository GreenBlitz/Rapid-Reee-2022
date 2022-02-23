package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Intake {
    private static Intake instance;
    private Roller roller;
    private Extender extender;

    private Intake() {
        roller = new Intake.Roller();
        extender = new Intake.Extender();
    }

    private static void init() {
        instance = new Intake();
        CommandScheduler.getInstance().registerSubsystem(instance.roller);
        CommandScheduler.getInstance().registerSubsystem(instance.extender);
    }

    public static Intake getInstance() {
        if(instance == null){
            init();
        }
        return instance;
    }

    public void moveRoller(double power) {
        roller.rollerMotor.set(power);
    }

    public void extend() {
        extender.extenderLeft.set(DoubleSolenoid.Value.kForward);
        extender.extenderRight.set(DoubleSolenoid.Value.kForward);
    }

    public void retract() {
        extender.extenderLeft.set(DoubleSolenoid.Value.kReverse);
        extender.extenderRight.set(DoubleSolenoid.Value.kReverse);
    }

    public boolean isExtended() {
        return extender.extenderLeft.get().equals(DoubleSolenoid.Value.kForward);
    }

    public void toggleExtender() {
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

        private WPI_TalonSRX rollerMotor;

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

        private DoubleSolenoid extenderLeft;
        private DoubleSolenoid extenderRight;

        private Extender() {
            extenderLeft = new DoubleSolenoid(RobotMap.Pegasus.Intake.PCM,
                    RobotMap.Pegasus.Intake.Solenoid.FORWARD,
                    RobotMap.Pegasus.Intake.Solenoid.REVERSE);
            extenderRight = new DoubleSolenoid(RobotMap.Pegasus.Intake.PCM,
                    RobotMap.Pegasus.Intake.Solenoid.FORWARD,
                    RobotMap.Pegasus.Intake.Solenoid.REVERSE);
        }

        private void setValue(DoubleSolenoid.Value value) {
            extenderRight.set(value);
            extenderLeft.set(value);
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
            putString("Left", extenderLeft.get().toString());
            putString("Right", extenderRight.get().toString());
        }
    }
}
