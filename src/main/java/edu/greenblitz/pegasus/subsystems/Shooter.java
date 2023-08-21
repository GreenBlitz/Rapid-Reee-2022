package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;

public class Shooter extends GBSubsystem {

    private static Shooter instance;

    private TalonSRX lowerMotor;
    private TalonSRX middleMotor;
    private CANSparkMax upperMotor;

    private Shooter(){


        upperMotor = new CANSparkMax( , CANSparkMaxLowLevel.MotorType.kBrushless);
      //todo set upper motor ID here ^
        upperMotor.setSmartCurrentLimit(24);
        middleMotor = new TalonSRX();
    //todo set middle motor id here ^
        lowerMotor = new TalonSRX();
    //todo set lowermotor id here ^
    }

    public static Shooter getInstance() {
        if (instance == null) instance = new Shooter();
        return instance;
    }

    public void setIntakePower(double speed){
        lowerMotor.set(TalonSRXControlMode.PercentOutput, speed);
    }

    public void setFunnelPower(double speed){
        middleMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setShooterPower(double speed){
        upperMotor.set(speed);
    }
}
