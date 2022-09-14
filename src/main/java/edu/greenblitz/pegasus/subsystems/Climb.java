package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.GBMotor;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.RailByJoystick;
import edu.greenblitz.pegasus.commands.climb.Turning.TurningByJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb extends GBSubsystem {

	// Rail motor moves the system forward and backward
	// Turning motor changes the angle of the system itself
	private final Rail rail;
	private final Turning turning;

	private boolean atStart;


	private Climb() {
		rail = new Rail();
		turning = new Turning();

	}

	private static Climb instance;

	public static Climb getInstance(){
		if (instance == null){
			instance = new Climb();
		}
		return instance;
	}

	public double getLoc() {
		double delta = getRailMotorRotations() / RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_ROTATIONS_PER_METER;
		double loc = delta + RobotMap.Pegasus.Climb.ClimbMotors.START_LOCATION;
		loc += (getAng() - RobotMap.Pegasus.Climb.ClimbMotors.START_ANGLE) * 0.022;
		return loc;
	}

	public double getAng() {
		double delta = getTurningMotorRotations() / RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_ROTATIONS_PER_RADIAN;
		double ang = delta + RobotMap.Pegasus.Climb.ClimbMotors.START_ANGLE;
		return ang;
	}

	public void safeMoveRailMotor(double power) {
		power = Math.min(Math.abs(power), 1) * Math.signum(power);
		double loc = getLoc();
		double len = RobotMap.Pegasus.Climb.ClimbMotors.RAIL_LENGTH;
		double safety = RobotMap.Pegasus.Climb.SafetyZones.RAIL_SAFETY;
		double safetyAngle = RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG;
		double safetyLoc = RobotMap.Pegasus.Climb.ClimbMotors.START_LOCATION;
//		if(getLoc() + safety >= safetyLoc && getAng() < safetyAngle && power > 0){
//			unsafeMoveRailMotor((safetyLoc - loc - absoluteSafety)/safety*power);
//		}
//		else
		if (loc - RobotMap.Pegasus.Climb.SafetyZones.RAIL_SAFETY_OFFSET < safety && power < 0) {
			unsafeMoveRailMotor(((Math.max(loc - RobotMap.Pegasus.Climb.SafetyZones.RAIL_SAFETY_OFFSET, 0) / safety) + RobotMap.Pegasus.Climb.SafetyZones.RAIL_FF) * power);
		} else if (loc + RobotMap.Pegasus.Climb.SafetyZones.RAIL_SAFETY_OFFSET + safety > len && power > 0) {
			unsafeMoveRailMotor(((Math.max(len - loc - RobotMap.Pegasus.Climb.SafetyZones.RAIL_SAFETY_OFFSET, 0) / safety) + RobotMap.Pegasus.Climb.SafetyZones.RAIL_FF) * power);
		} else {
			unsafeMoveRailMotor(power);
		}

	}

	public void unsafeMoveRailMotor(double power) {
		rail.railMotor.setPower(power);
	}

	public void safeMoveTurningMotor(double power) {
		power = Math.min(Math.abs(power), 1) * Math.signum(power);
		double ang = getAng();
		double safety = RobotMap.Pegasus.Climb.SafetyZones.TURN_SAFETY;
		double absoluteSafety = RobotMap.Pegasus.Climb.SafetyZones.TURN_ABSOLUTE_SAFETY;
		double min = RobotMap.Pegasus.Climb.SafetyZones.LOWEST_ANGLE;
		double max = RobotMap.Pegasus.Climb.SafetyZones.HIGHEST_ANGLE;
		double safetyAngle = RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG;
		double safetyLoc = RobotMap.Pegasus.Climb.SafetyZones.SAFETY_LOC;
//		if(getLoc() > safetyLoc && ang - safety < safetyAngle && power < 0){
//			unsafeMoveTurningMotor((ang - absoluteSafety - safetyAngle)/safety*power);
//		}
//		else
		if (ang - safety < min && power < 0) {
			unsafeMoveTurningMotor(Math.max(ang - absoluteSafety - min, 0) / safety * power);
		} else if (ang + safety > max && power > 0) {
			unsafeMoveTurningMotor((Math.max(max - ang - absoluteSafety, 0) / safety * power));
		} else {
			unsafeMoveTurningMotor(power);
		}
	}

	public void unsafeMoveTurningMotor(double power) {
		turning.turningMotor.setPower(power);
	}

	public double getRailMotorRotations() {
		return rail.railMotor.getNormalizedPosition();
	}

	public void resetRailEncoder() {
		rail.railMotor.resetEncoder();
	}

	public double getTurningMotorRotations() {
		return turning.turningMotor.getNormalizedPosition();
	}

	public void resetTurningMotorRotations() {
		turning.turningMotor.resetEncoder();
	}

	public AbstractMotor.IdleMode getTurningMotorIdle() {
		return turning.turningMotor.getIdleMode();
	}

	public void setTurningMotorIdle(AbstractMotor.IdleMode mode) {
		turning.turningMotor.setIdleMode(mode);
	}

	public void setRailPIDValues(PIDObject pid) {
		rail.railMotor.configurePID(pid);
	}

	public void setTurningPIDValues(PIDObject pid) {
		turning.turningMotor.configurePID(pid);
	}

	public Turning getTurning() {
		return turning;
	}

	public Rail getRail() {
		return rail;
	}

	public boolean getAtStart() {
		return atStart;
	}

	public void initDefaultCommand(SmartJoystick joystick) {
		turning.setDefaultCommand(new TurningByJoystick(joystick));
		rail.setDefaultCommand(new RailByJoystick(joystick));

	}

	@Override
	public void periodic() {
		SmartDashboard.putNumber("climbAngle", getAng());
		atStart = false;
		if (Math.abs(getLoc() - RobotMap.Pegasus.Climb.ClimbMotors.START_LOCATION) < RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON) {
			if (Math.abs(getAng() - RobotMap.Pegasus.Climb.ClimbMotors.START_ANGLE) < RobotMap.Pegasus.Climb.ClimbConstants.Rotation.EPSILON) {
				atStart = true;
			}
		}
	}

	private class ClimbSubsystem extends GBSubsystem {
		public Climb getClimb() {
			return Climb.this;
		}
	}

	private class Rail extends ClimbSubsystem {

		private final GBMotor railMotor;


		private Rail() {
			railMotor = new SparkMaxFactory()
					.withInverted(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_REVERSED)
					.withIdleMode(AbstractMotor.IdleMode.Brake)
					.generate(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_MOTOR_PORT);
			railMotor.resetEncoder();
		}
	}

	private class Turning extends ClimbSubsystem {
		private final GBMotor turningMotor;

		private Turning() {
			turningMotor = new SparkMaxFactory()
					.withInverted(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_REVERSED)
					.withIdleMode(AbstractMotor.IdleMode.Brake)
					.generate(RobotMap.Pegasus.Climb.ClimbMotors.TURNING_MOTOR_PORT);
			turningMotor.resetEncoder();
		}

		public void toCoast() {
			this.turningMotor.setIdleMode(AbstractMotor.IdleMode.Coast);
		}

		public void toBrake() {
			this.turningMotor.setIdleMode(AbstractMotor.IdleMode.Brake);
		}

//		private boolean needsCoast = false;

		@Override
		public void periodic() {
			super.periodic();
//			if (getAng() > Math.PI / 2 - 0.3 && turningMotor.getIdleMode() == CANSparkMax.IdleMode.kCoast) {
//				needsCoast = true;
//				setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
//			}
//			if (getAng() < Math.PI / 2 - 0.4 && needsCoast) {
//				needsCoast = false;
//				setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
//			}
			SmartDashboard.putNumber("Rail loc", this.getClimb().getLoc());
		}
	}
}

