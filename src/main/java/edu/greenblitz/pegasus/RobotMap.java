package edu.greenblitz.pegasus;


import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.subsystems.swerve.KazaSwerveModule;
import edu.greenblitz.pegasus.subsystems.swerve.SdsSwerveModule;
import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.utils.cords.Point;
import edu.greenblitz.pegasus.utils.motors.GBFalcon;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.greenblitz.motion.interpolation.Dataset;

public class RobotMap {
	public static class GameField{
		//todo place real coordinates
		public static final Point SHOOTING_TARGET_POINT = new Point(5,5); //currently one point maybe later add more

	}
	public static class Pegasus {
		public static class General {
			public final static double minVoltageBattery = 11;
			
			public static class Motors {
				public final static double SPARKMAX_TICKS_PER_RADIAN = Math.PI * 2;
				public final static double SPARKMAX_VELOCITY_UNITS_PER_RPM = 1;
				public static final double NEO_PHYSICAL_TICKS_TO_RADIANS = SPARKMAX_TICKS_PER_RADIAN / 42; //do not use unless you understand the meaning
				
				public final static double FALCON_TICKS_PER_RADIAN = 2 * Math.PI / 2048.0;
				public final static double FALCON_VELOCITY_UNITS_PER_RPM = 600.0 / 2048;
			}
			
			public final static double VOLTAGE_COMP_VAL = 11.5;
			public final static double RAMP_RATE_VAL = 0.4;
		}
		
		public static class gyro {
			public static final int pigeonID = 12;
		}
		
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
			
			public static final int debug = 2;
		}
		
		public static class Intake {
			
			public static final double POWER = 1.0;
			public static final double REVERSE_POWER = -1.0;
			
			public static class Motors {
				public static final int ROLLER_PORT = 6;
				public static final boolean IS_REVERSED = false;
			}
			
			public static class Solenoid {
				public static final int FORWARD_PORT = 1;
				public static final int REVERSE_PORT = 0;
			}
		}
		
		public static class Shooter {
			
			public static class Hood{
				public static final int MOTOR_ID = 8;

				public static final int LIMIT_SWITCH = 3;


				//todo calibrate
				public static final double kS = 0;
				public static final double kV = 0;
				public static final double kA = 0;

				public static final SimpleMotorFeedforward FEED_FORWARD = new SimpleMotorFeedforward(Hood.kS, Hood.kV, Hood.kA);


				public static final double GEAR_RATIO = 1;
				public static final PIDObject PID = new PIDObject(1, 1, 0);

				public static final GBSparkMax.SparkMaxConfObject MOTOR_CONF =  new GBSparkMax.SparkMaxConfObject()
						.withInverted(false) //whether the motor should be flipped
						.withCurrentLimit(40) // the max current to allow should be inline with the fuse
						.withIdleMode(CANSparkMax.IdleMode.kBrake) // trying to force brake is harmful for the motor
						.withRampRate(General.RAMP_RATE_VAL) // prevents the motor from drawing to much when rapidly changing speeds
						.withVoltageComp(General.VOLTAGE_COMP_VAL) // makes for more reproducible results
						.withPositionConversionFactor(GEAR_RATIO)
						.withVelocityConversionFactor(GEAR_RATIO)
						.withPID(PID);


				public static final double TOLERANCE = Units.degreesToRadians(3);

			}
			public static class FlyWheel {
				public static final int ID = 7;

				// devided by 60 because the SysID is in RPS and our code is in RPM
				public static final double ks = 0.31979 / 60; //todo
				public static final double kv = 0.13012 / 60;
				public static final double ka = 0.017243 / 60;
				
				public static final GBSparkMax.SparkMaxConfObject FLYWHEEL_CONF = new GBSparkMax.SparkMaxConfObject()
						.withInverted(true) //whether the motor should be flipped
						.withCurrentLimit(40) // the max current to allow should be inline with the fuse
						.withIdleMode(CANSparkMax.IdleMode.kCoast) // trying to force brake is harmful for the motor
						.withRampRate(General.RAMP_RATE_VAL) // prevents the motor from drawing to much when rapidly changing speeds
						.withVoltageComp(General.VOLTAGE_COMP_VAL) // makes for more reproducible results
						.withPositionConversionFactor(1) // todo the gear ratio was not used on the shooter at any point this year should change but not trivial
						.withVelocityConversionFactor(1)
						.withPID(new PIDObject(0.0003, 0.0000003, 0).withIZone(300));



				public static final SimpleMotorFeedforward FEED_FORWARD = new SimpleMotorFeedforward(FlyWheel.ks, FlyWheel.kv, FlyWheel.ka);

				public static final double TOLERANCE = 5; //RPM

			}
			
		

			public static final Dataset DISTANCE_TO_SHOOTING = new Dataset(2);


			
			static {
				
				//<distance, hood angle (rads), RPM for shooter >
				DISTANCE_TO_SHOOTING.addDatapoint(0, new double[]{Units.degreesToRadians(80), 300});
				DISTANCE_TO_SHOOTING.addDatapoint(1, new double[]{Units.degreesToRadians(70), 300});
				DISTANCE_TO_SHOOTING.addDatapoint(2, new double[]{Units.degreesToRadians(60), 300});
				DISTANCE_TO_SHOOTING.addDatapoint(3, new double[]{Units.degreesToRadians(50), 300});
				DISTANCE_TO_SHOOTING.addDatapoint(4, new double[]{Units.degreesToRadians(40), 300});
				DISTANCE_TO_SHOOTING.addDatapoint(5, new double[]{Units.degreesToRadians(30), 300});
			}
		}
		
		public static class Funnel {
			public static final double POWER = 0.7;
			public static final double REVERSE_POWER = -0.7;
			public static final int MACRO_SWITCH_PORT = 1;
			
			public static class FunnelMotor {
				public static final int MOTOR_PORT = 5;
				public static final boolean IS_REVERSED = true;
			}
		}
		
		public static class Pneumatics {
			public static class PCM {
				public static final int PCM_ID = 22;
				public static final PneumaticsModuleType PCM_TYPE = PneumaticsModuleType.CTREPCM;
			}
			
			public static class PressureSensor {
				public static final int PRESSURE = 3;
			}
		}
		
		public static class Vision {
			public static final Transform2d initialCamPosition = new Transform2d(new Translation2d(), new Rotation2d());
			public static final Pose3d apriltagLocation = new Pose3d(new Translation3d(5, 5, 0), new Rotation3d(0, 0, Math.PI));
			
		}
		
		public static class Swerve {
			public static class KazaSwerve {
				public static final double ANG_GEAR_RATIO = 6.0;
				public static final double LIN_GEAR_RATIO = 8.0;
				
				
				public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI;
				public static final double linTicksToMeters = RobotMap.Pegasus.General.Motors.SPARKMAX_TICKS_PER_RADIAN * WHEEL_CIRC / LIN_GEAR_RATIO;
				public static final double angleTicksToWheelToRPM = RobotMap.Pegasus.General.Motors.SPARKMAX_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
				public static final double linTicksToMetersPerSecond = RobotMap.Pegasus.General.Motors.SPARKMAX_VELOCITY_UNITS_PER_RPM * WHEEL_CIRC / 60 / LIN_GEAR_RATIO;
				public static final double angleTicksToRadians = RobotMap.Pegasus.General.Motors.SPARKMAX_TICKS_PER_RADIAN / ANG_GEAR_RATIO;
				
				public static final PIDObject angPID = new PIDObject().withKp(0.5).withMaxPower(1.0);
				public static final GBSparkMax.SparkMaxConfObject baseAngConfObj =
						new GBSparkMax.SparkMaxConfObject()
								.withIdleMode(CANSparkMax.IdleMode.kBrake)
								.withCurrentLimit(30)
								.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
								.withVoltageComp(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
								.withInverted(true)
								.withPID(angPID)
								.withPositionConversionFactor(angleTicksToRadians)
								.withVelocityConversionFactor(angleTicksToWheelToRPM);
				
				public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
				public static final GBSparkMax.SparkMaxConfObject baseLinConfObj =
						new GBSparkMax.SparkMaxConfObject()
								.withIdleMode(CANSparkMax.IdleMode.kBrake)
								.withCurrentLimit(40)
								.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
								.withVoltageComp(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
								.withPID(linPID)
								.withPositionConversionFactor(linTicksToMeters)
								.withVelocityConversionFactor(linTicksToMetersPerSecond);
			}
			
			public static class SdsSwerve {
				public static final double ANG_GEAR_RATIO = (150.0 / 7);
				public static final double LIN_GEAR_RATIO = 8.14;
				
				public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI;
				public static final double linTicksToMeters = RobotMap.Pegasus.General.Motors.FALCON_TICKS_PER_RADIAN * WHEEL_CIRC / LIN_GEAR_RATIO;
				public static final double angleTicksToWheelToRPM = RobotMap.Pegasus.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM / ANG_GEAR_RATIO;
				public static final double linTicksToMetersPerSecond = RobotMap.Pegasus.General.Motors.FALCON_VELOCITY_UNITS_PER_RPM / LIN_GEAR_RATIO * WHEEL_CIRC / 60;
				public static final double angleTicksToRadians = RobotMap.Pegasus.General.Motors.FALCON_TICKS_PER_RADIAN / ANG_GEAR_RATIO;
				public static final double magEncoderTicksToFalconTicks = 2 * Math.PI / angleTicksToRadians;
				
				public static final PIDObject angPID = new PIDObject().withKp(0.05).withMaxPower(1.0).withFF(0);//.withKd(10).withMaxPower(0.8);
				public static final GBFalcon.FalconConfObject baseAngConfObj =
						new GBFalcon.FalconConfObject()
								.withNeutralMode(NeutralMode.Brake)
								.withCurrentLimit(30)
								.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
								.withVoltageCompSaturation(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
								.withInverted(true)
								.withPID(angPID);
				
				public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
				public static final GBFalcon.FalconConfObject baseLinConfObj =
						new GBFalcon.FalconConfObject()
								.withNeutralMode(NeutralMode.Brake)
								.withCurrentLimit(40)
								.withRampRate(RobotMap.Pegasus.General.RAMP_RATE_VAL)
								.withVoltageCompSaturation(RobotMap.Pegasus.General.VOLTAGE_COMP_VAL)
								.withPID(linPID);
			}
			
			public static final Pose2d initialRobotPosition = new Pose2d(0, 0, new Rotation2d(0));
			public static final Translation2d[] SwerveLocationsInSwerveKinematicsCoordinates = new Translation2d[]{
					//the WPILib coordinate system is stupid. (x is forwards, y is leftwards)
					//the translations are given rotated by 90 degrees clockwise to avoid coordinates system conversion at output
					new Translation2d(0.3020647, 0.25265), /*fl*/
					new Translation2d(0.3020647, -0.25265),/*fr*/
					new Translation2d(-0.3020647, 0.25265),/*bl*/
					new Translation2d(-0.3020647, -0.25265)/*br*/
			};
			
			
			public static final double MAX_VELOCITY = 4.1818320981472068;
			public static final double MAX_ANGULAR_SPEED = 10.454580245368017;
			
			public static final PIDObject rotationPID = new PIDObject().withKp(0.5).withKi(0).withKd(0).withFF(0.1);
			
			
			public static final double ks = 0.14876;
			public static final double kv = 3.3055;
			public static final double ka = 0.11023;
			
			
			public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModule1 =
					new KazaSwerveModule.KazaSwerveModuleConfigObject(1, 10, 0, false); //front left
			
			public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModule2 =
					new KazaSwerveModule.KazaSwerveModuleConfigObject(3, 11, 2, true); //front right
			
			public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModule3 =
					new KazaSwerveModule.KazaSwerveModuleConfigObject(2, 8, 1, false); //back left
			
			public static KazaSwerveModule.KazaSwerveModuleConfigObject KazaModule4 =
					new KazaSwerveModule.KazaSwerveModuleConfigObject(12, 5, 3, true); //back right
			
			public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModule1 =
					new SdsSwerveModule.SdsSwerveModuleConfigObject(1, 0, 3, false, 3.4635 / (2 * Math.PI)); //front left
			
			public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModule2 =
					new SdsSwerveModule.SdsSwerveModuleConfigObject(3, 2, 1, true, 4.55 / (2 * Math.PI)); //front right
			
			public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModule3 =
					new SdsSwerveModule.SdsSwerveModuleConfigObject(5, 4, 2, false, 3.947 / (2 * Math.PI)); //back left
			
			public static SdsSwerveModule.SdsSwerveModuleConfigObject SdsModule4 =
					new SdsSwerveModule.SdsSwerveModuleConfigObject(7, 6, 0, true, 5.386 / (2 * Math.PI)); //back right
			
		}
		
	}
}