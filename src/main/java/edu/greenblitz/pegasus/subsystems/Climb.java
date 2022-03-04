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
		railEncoder.reset();
		
		turningMotor = new CANSparkMax(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		turningMotor.setInverted(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_REVERSED);
		turningMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
		turningEncoder = new SparkEncoder(new GearDependentValue<>(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_TICKS_PER_RADIAN, RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_TICKS_PER_RADIAN), turningMotor);
		turningEncoder.reset();
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

public double getLoc(){
	double delta = getRailMotorTicks() / RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_TICKS_PER_METER;
	double loc = delta + RobotMap.Pegasus.Climb.ClimbMotors.START_LOCATION;
	loc += (getAng() - RobotMap.Pegasus.Climb.ClimbMotors.START_ANGLE)*0.022;
	return loc;
}

public double getAng(){
		double delta = getTurningMotorTicks() / RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_TICKS_PER_RADIAN;
		double ang = delta + RobotMap.Pegasus.Climb.ClimbMotors.START_ANGLE;
		return ang;
	}

	public void safeMoveRailMotor(double power) {
		double loc = getLoc();
		double len = RobotMap.Pegasus.Climb.ClimbMotors.RAIL_LENGTH;
		double safety = RobotMap.Pegasus.Climb.ClimbMotors.RAIL_SAFETY;
		double absoluteSafety = RobotMap.Pegasus.Climb.ClimbMotors.RAIL_ABSOLUTE_SAFETY;
		double safetyAngle = RobotMap.Pegasus.Climb.ClimbMotors.SAFETY_ANGLE;
		double safetyLoc = RobotMap.Pegasus.Climb.ClimbMotors.SAFETY_LOC;
		if(getLoc() + safety > safetyLoc && getAng() < safetyAngle && power > 0){
			unsafeMoveRailMotor((safetyLoc - loc - absoluteSafety)/safety*power);
		}
		else if (loc < safety && power < 0){
			unsafeMoveRailMotor((loc - absoluteSafety)/safety*power);
		}
		else if(loc + safety > len && power > 0){
			unsafeMoveRailMotor((len - loc - absoluteSafety)/safety*power);
		}
		else{
			unsafeMoveRailMotor(power);
		}
		
	}
	
	public void unsafeMoveRailMotor(double power){
		railMotor.set(power);
	}



	public void safeMoveTurningMotor(double power){
		double ang = getAng();
		double safety = RobotMap.Pegasus.Climb.ClimbMotors.TURN_SAFETY;
		double absoluteSafety = RobotMap.Pegasus.Climb.ClimbMotors.TURN_ABSOLUTE_SAFETY;
		double min = RobotMap.Pegasus.Climb.ClimbMotors.LOWEST_ANGLE;
		double max = RobotMap.Pegasus.Climb.ClimbMotors.HIGHEST_ANGLE;
		double safetyAngle = RobotMap.Pegasus.Climb.ClimbMotors.SAFETY_ANGLE;
		double safetyLoc = RobotMap.Pegasus.Climb.ClimbMotors.SAFETY_LOC;
		if(getLoc() > safetyLoc && ang - safety < safetyAngle && power < 0){
			unsafeMoveTurningMotor((ang - absoluteSafety - safetyAngle)/safety*power);
		}
		else if (ang - safety < min && power < 0){
			unsafeMoveTurningMotor((ang - absoluteSafety - min)/safety*power);
		}
		else if(ang + safety > max && power > 0){
			unsafeMoveTurningMotor((max - ang - absoluteSafety)/safety*power);
		}
		else{
			unsafeMoveTurningMotor(power);
		}
	}

	public void unsafeMoveTurningMotor(double power) {
		turningMotor.set(power);
	}

	public double getRailMotorTicks() {
		return railEncoder.getRawTicks();
	}
	
	public void resetRailMotorTicks(){
		railEncoder.reset();
	}

	public double getTurningMotorTicks() {
		return turningEncoder.getRawTicks();
	}

	public void resetTurningMotorTicks(){
		turningEncoder.reset();
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
