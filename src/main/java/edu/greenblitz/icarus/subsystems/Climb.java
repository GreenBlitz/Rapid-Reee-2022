package edu.greenblitz.icarus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.icarus.RobotMap;

public class Climb extends GBSubsystem{
    private static Climb instance;
    private CANSparkMax motor;
    private SparkEncoder encoder;

    private Climb(){
        motor = new CANSparkMax(RobotMap.Icarus.Climb.Motor.MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.setInverted(RobotMap.Icarus.Climb.Motor.MOTOR_REVERSE);
        encoder = new SparkEncoder(RobotMap.Icarus.Climb.Motor.MOTOR_TICKS_PER_METER, motor);
    }

    @Override
    public void periodic() {
        super.periodic();
        //Add data to smartdashboard here
    }

    private static void init(){
        instance = new Climb();
    }

    public static Climb getInstance(){
        if(instance == null){
            init();
        }
        return instance;
    }

    private void moveMotor(double power){
        motor.set(power);
    }

    public void safeMoveMotor(double power){
        if(power < 0 || power > 1){

        }
    }
}
