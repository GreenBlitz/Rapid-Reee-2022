package edu.greenblitz.icarus.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.greenblitz.icarus.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
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
        if(instance != null){
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
            rollerMotor = new WPI_TalonSRX(RobotMap.Icarus.Intake.Motors.ROLLER_PORT);
            rollerMotor.setInverted(RobotMap.Icarus.Intake.Motors.IS_REVERSED);
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
            extenderLeft = new DoubleSolenoid(RobotMap.Icarus.Intake.PCM,
                    RobotMap.Icarus.Intake.Solenoid.FORWARD_LEFT,
                    RobotMap.Icarus.Intake.Solenoid.REVERSE_LEFT);
            extenderRight = new DoubleSolenoid(RobotMap.Icarus.Intake.PCM,
                    RobotMap.Icarus.Intake.Solenoid.FORWARD_RIGHT,
                    RobotMap.Icarus.Intake.Solenoid.REVERSE_RIGHT);
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
