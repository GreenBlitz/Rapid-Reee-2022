package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMaxPIDController;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.greenblitz.gblib.gears.GearDependentValue;
import edu.greenblitz.pegasus.RobotMap;
import org.greenblitz.motion.pid.PIDObject;

public class Climb extends GBSubsystem {
	private static Climb instance;

	// Rail motor moves the system forward and backward
	// Turning motor changes the angle of the system itself
	private CANSparkMax railMotor, turningMotor;
	private SparkEncoder railEncoder, turningEncoder;
	private boolean turningMode = false;

	private Climb() {
		railMotor = new CANSparkMax(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		railMotor.setInverted(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_REVERSED);
		railEncoder = new SparkEncoder(new GearDependentValue<>(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_TICKS_PER_METER, RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_TICKS_PER_METER), railMotor);

		turningMotor = new CANSparkMax(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		turningMotor.setInverted(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_REVERSED);
		turningEncoder = new SparkEncoder(new GearDependentValue<>(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_TICKS_PER_RADIAN, RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_TICKS_PER_RADIAN), turningMotor);
	}

	@Override
	public void periodic() {
		super.periodic();
		//Add data to smartdashboard here
	}

	private static void init() {
		instance = new Climb();
	}

	public static Climb getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}

	public void moveRailMotor(double power) {
		railMotor.set(power);
	}

	public void moveTurningMotor(double power) {
		turningMotor.set(power);
	}

	public double getRailMotorTicks() {
		return railEncoder.getRawTicks();
	}

	public double getTurningMotorTicks() {
		return turningEncoder.getRawTicks();
	}

	public void setRailPIDValues(PIDObject pid) {
		SparkMaxPIDController controller = railMotor.getPIDController();
		controller.setP(pid.getKp());
		controller.setI(pid.getKi());
		controller.setD(pid.getKd());
		controller.setFF(pid.getKf());
	}

	public void setTurningPIDValues(PIDObject pid) {
		SparkMaxPIDController controller = turningMotor.getPIDController();
		controller.setP(pid.getKp());
		controller.setI(pid.getKi());
		controller.setD(pid.getKd());
		controller.setFF(pid.getKf());
	}
}
