package edu.greenblitz.pegasus;


import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.greenblitz.motion.interpolation.Dataset;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.SafetyZones.HIGHEST_ANGLE;

public class RobotMap {
	public static class Pegasus {
		
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
		}

//		public static class Chassis {
//			public static final double WHEEL_DIST = 0.564; //very accurate right now
//			public static final double startAngle = Math.toRadians(23);
//
//			public static class Motors {
//				public static final int[] ports = {1, 2, 3, 4, 5, 6};
//				public static final boolean[] isInverted = {true, true, true, false, false, false};
//				public static final int RIGHT_LEADER = 1, RIGHT_FOLLOWER_1 = 2, RIGHT_FOLLOWER_2 = 3, LEFT_LEADER = 4, LEFT_FOLLOWER_1 = 5, LEFT_FOLLOWER_2 = 6;
//				public static final boolean RIGHT_LEADER_REVERSED = true, RIGHT_FOLLOWER_1_REVERSED = true, RIGHT_FOLLOWER_2_REVERSED = true, LEFT_LEADER_REVERSED = false, LEFT_FOLLOWER_1_REVERSED = false, LEFT_FOLLOWER_2_REVERSED = false;
//			}
//
//			public static class Encoders {
//				public static final GearDependentValue NORM_CONST_SPARK = new GearDependentValue(2300.0 * 0.64, 1234.0 / 2.0); // TODO: check this, I copied it from Infinite Reeee
//				public static final int RIGHT_ENCODER = -1, LEFT_ENCODER = -1;
//				public static final boolean RIGHT_ENCODER_REVERSED = false, LEFT_ENCODER_REVERSED = false;
//			}
//
//			public static class Shifter {
//				public static final DoubleSolenoid.Value POWER_VALUE = DoubleSolenoid.Value.kForward;
//				public static final DoubleSolenoid.Value SPEED_VALUE = DoubleSolenoid.Value.kReverse;
//
//				public static class Solenoid {
//					public static final int FORWARD_PORT = 3;
//					public static final int REVERSE_PORT = 1;
//				}
//			}
//
//			public static class MotionData { // TODO: calibrate this
//
//				public static final ProfilingConfiguration CONFIG = new ProfilingConfiguration(0.85, 1.0, .0005, 0.8, 0.0, 2.0, .01, 0.5 * 0, 0, 0, .01, 500);
//				public static HashMap<String, ProfilingData> POWER;
//				public static HashMap<String, ProfilingData> SPEED;
//				public static GearDependentValue<HashMap<String, ProfilingData>> PROF;
//
//				static {
//
//					POWER = new HashMap<>();
//					SPEED = new HashMap<>();
//					PROF = new GearDependentValue<>(null, null);
//
//					POWER.put("0.5", new ProfilingData(1.4, 8.4, 4, 10));
//
//					PROF.setValue(true, POWER);
//					PROF.setValue(false, SPEED);
//				}
//
//			}
//
//
//		}

		public static class Intake {

			public static final double POWER = 1.0;
			public static final double REVERSE_POWER = -1.0;

			public static class Motors {
				public static final int ROLLER_PORT = 6;
				public static final boolean IS_REVERSED = false;
			}

			public static class Solenoid {
				public static final int FORWARD_PORT = 2;
				public static final int REVERSE_PORT = 0;
			}
		}

		public static class Shooter {
			public static class ShooterMotor {
				public static final int PORT_LEADER = 7; /*,
				public static final int PORT_LEADER = 7; /*,
						PORT_FOLLOWER = 2;*/
				public static final boolean LEADER_INVERTED = true;  /*,
						FOLLOWER_INVERTED = false;*/


				public static final Dataset RPM_TO_POWER = new Dataset(2);
				public static final double RPM = 2350; // Should be 2300
				public static final PIDObject pid = new PIDObject(0.0002, 0.0000003, 0).withIZone(300); //d1: 0.0001, 0.0000003, 0

				static {
					RPM_TO_POWER.addDatapoint(0, new double[]{-0.0000000001});
					RPM_TO_POWER.addDatapoint(346.17146728515627, new double[]{0.1});
					RPM_TO_POWER.addDatapoint(840.5715405273437, new double[]{0.2});
					RPM_TO_POWER.addDatapoint(1352.4570483398438, new double[]{0.3});
					RPM_TO_POWER.addDatapoint(1890.8568017578125, new double[]{0.4});
					RPM_TO_POWER.addDatapoint(2411.9998046875, new double[]{0.5});
					RPM_TO_POWER.addDatapoint(2904.800322265625, new double[]{0.6});
					RPM_TO_POWER.addDatapoint(3518.057314453125, new double[]{0.7});
					RPM_TO_POWER.addDatapoint(4140.91421875, new double[]{0.8});
					RPM_TO_POWER.addDatapoint(4664.115322265625, new double[]{0.9});
					RPM_TO_POWER.addDatapoint(5209.37181640625, new double[]{1.0});
				}
			}
		}

		public static class Funnel {
			public static final double POWER = 0.7;
			public static final double REVERSE_POWER = -0.7;
			public static final int MACRO_SWITCH_PORT = 0;

			public static class FunnelMotor {
				public static final int MOTOR_PORT = 5;
				public static final boolean IS_REVERSED = true;
			}
		}

		public static class Climb {
			public static class SafetyZones {
				public static final double RAIL_SAFETY_OFFSET = 0.02;
				public static final double RAIL_SAFETY = 0.12;
				public static final double RAIL_FF = 0.1;
				public static final double SAFETY_LOC = 0.6;
				public static final double LOWEST_ANGLE = Math.toRadians(14.3);
				public static final double HIGHEST_ANGLE = Math.toRadians(72.7);
				public static final double TURN_SAFETY = 0.05;
				public static final double TURN_ABSOLUTE_SAFETY = 0.02;
				public static final double BATTERY_SAFETY_ANG = Math.toRadians(45.5); // Math.toDegrees(51.5)
				public static final double BATTERY_SAFETY_LOC = 0.35;
			}

			public static class ClimbMotors {
				public static final int RAIL_MOTOR_PORT = 9;
				public static final boolean RAIL_MOTOR_REVERSED = false;
				public static final double RAIL_MOTOR_ROTATIONS_PER_METER = 203.43;
				public static final double RAIL_LENGTH = 0.89;
				public static final double START_LOCATION = 0.633;
				public static final int TURNING_MOTOR_PORT = 11;
				public static final boolean TURNING_MOTOR_REVERSED = false;
				public static final double TURNING_MOTOR_ROTATIONS_PER_RADIAN = 50.9;
				public static final double START_ANGLE = Math.toRadians(15.985);
				public static final double MID_START_ANGLE = Math.toRadians(20) + START_ANGLE;
			}

			public static class ClimbConstants {
				public static class Rotation {
					public static final double kp = 0.3 / Math.PI * 2;
					public static final double kp_static = 0.2 / Math.PI * 2;
					public static final double ff = 0.2;
					public static final double ff_static = 0.15;

					public static final double RADIANS_TO_SECOND_BAR = Math.toRadians(39);
					public static final double RADIANS_TO_TRAVERSAL = Math.toRadians(0); //TODO: change this
					public static final double RADIANS_TO_MID_GAME = HIGHEST_ANGLE - Math.toRadians(2.5);
					public static final double EPSILON = Math.toRadians(2);
					public static final double EPSILON_STATIC = Math.toRadians(2);
				}

				public static class Rail {
					public static final double kp = 3; //10
					public static final double ff = 0.2;
					public static final double METERS_TO_SECOND_BAR = 0.0;
					public static final double METERS_TO_TRAVERSAL = 0.0;
					public static final double METERS_TO_MID_GAME = 0.41;
					public static final double STOPPER_SAFETY_THRESH = 0.1;
					public static final double EPSILON = 0.01;
				}
			}
		}

		public static class Pneumatics {
			public static class PCM {
				public static final int PCM_ID = 21;
				public static final PneumaticsModuleType PCM_TYPE = PneumaticsModuleType.CTREPCM;
			}

			public static class PressureSensor {
				public static final int PRESSURE = 3;
			}
		}

		public static class DigitalInputMap {
			public static final int MACRO_SWITCH = 0;
		}

		public static class Swerve {
			public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI; //very accurate right now

			public static final double ANG_GEAR_RATIO = 6.0;
			public static final double LIN_GEAR_RATIO = 8.0;
//			public static final double MODULE_OFFSET_X = 30.2645;
//			public static final double MODULE_OFFSET_Y = 25.2645;
//			public static final Translation2d[] SwerveLocations = new Translation2d[]{
//					new Translation2d(MODULE_OFFSET_X,MODULE_OFFSET_Y),
//					new Translation2d(-MODULE_OFFSET_X,MODULE_OFFSET_Y),
//					new Translation2d(MODULE_OFFSET_X,-MODULE_OFFSET_Y),
//					new Translation2d(-MODULE_OFFSET_X,-MODULE_OFFSET_Y)
//			};
			public static final Translation2d[] SwerveLocationsInSwerveKinematicsCoordinates = new Translation2d[]{
					//the WPILib coordinate system is stupid. (x is forwards, y is letwards)
					//the translations are given rotated by 90 degrees clockwise to avoid coordinates system conversion at output
//					new Translation2d(0.3020647,0.25265), fl
//					new Translation2d(-0.3020647,0.25265),fr
//					new Translation2d(0.3020647,-0.25265),bl
//					new Translation2d(-0.3020647,-0.25265)br
					new Translation2d(-0.3020647,-0.25265),
					new Translation2d(-0.3020647,0.25265),
					new Translation2d(0.3020647,-0.25265),
					new Translation2d(0.3020647,0.25265)

			};
			//TODO: calibrate GOOD pid
			
			public static final PIDObject angPID = new PIDObject().withKp(0.5).withKd(10).withMaxPower(0.5);
			public static final PIDObject linPID = new PIDObject().withKp(0.5);//PIDObject().withKp(0.0003).withMaxPower(0.5);
			public static final PIDObject rotationPID = new PIDObject().withKp(0.3).withKi(0).withKd(0);
			
			
			public static final double ks = 0.14876;
			public static final double kv = 3.3055;
			public static final double ka = 0.11023;
			
			public static final double KMaxVelocity = 3.7; //todo find real max speed (meters per second)
			public static final double KMMaxAcceleration = 1; //todo find real max acceleration (meters per second ^2)
			public static final double KMaxAngularVelocity = 9.39; //todo find real max speed (radians per second)
			public static final double KMMaxAngularAcceleration = 1; //todo find real max acceleration (radians per second ^2)
			
			public static class Module1 {//front right
				public static final int linMotorID = 11;
				public static final int SteerMotorID = 3;
				public static final int lampryID = 2;
				public static final int MIN_LAMPREY_VAL = 28;
				public static final int MAX_LAMPREY_VAL = 2675;
				public static final boolean INVERTED = false;
			}

			public static class Module2 {//front left
				public static final int linMotorID = 10;
				public static final int SteerMotorID =1;
				public static final int lampryID = 0 ;

				public static final int MIN_LAMPREY_VAL = 16;
				public static final int MAX_LAMPREY_VAL = 2669;
				public static final boolean INVERTED = false;
			}

			public static class Module3 {//back right
				public static final int linMotorID = 5;
				public static final int SteerMotorID =12;
				public static final int lampryID = 3;

				public static final int MIN_LAMPREY_VAL = 8;
				public static final int MAX_LAMPREY_VAL = 2670;
				public static final boolean INVERTED = true;
			}

			public static class Module4 {//back left
				public static final int linMotorID = 8;
				public static final int SteerMotorID =2;
				public static final int lampryID =  1;
				public static final int MIN_LAMPREY_VAL = 20;
				public static final int MAX_LAMPREY_VAL = 2646;
				public static final boolean INVERTED = true;
			}
		}

	}
}