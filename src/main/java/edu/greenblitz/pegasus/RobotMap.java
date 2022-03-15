package edu.greenblitz.pegasus;

import edu.greenblitz.gblib.gears.Gear;
import edu.greenblitz.gblib.gears.GearDependentValue;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.greenblitz.motion.interpolation.Dataset;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ProfilingConfiguration;
import org.greenblitz.motion.profiling.ProfilingData;

import java.util.HashMap;

public class RobotMap {
	public static class Pegasus {
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
		}

		public static class Chassis {
			public static final double WHEEL_DIST = 0.564;

			public static class Motors {
				public static final int RIGHT_LEADER = 1, RIGHT_FOLLOWER_1 = 2, RIGHT_FOLLOWER_2 = 3, LEFT_LEADER = 4, LEFT_FOLLOWER_1 = 5, LEFT_FOLLOWER_2 = 6;
				public static final boolean RIGHT_LEADER_REVERSED = true, RIGHT_FOLLOWER_1_REVERSED = true, RIGHT_FOLLOWER_2_REVERSED = true, LEFT_LEADER_REVERSED = false, LEFT_FOLLOWER_1_REVERSED = false, LEFT_FOLLOWER_2_REVERSED = false;
			}

			public static class Encoders {
				public static final GearDependentValue<Double> NORM_CONST_SPARK = new GearDependentValue<>(1622.0, 612.5); // TODO: check this, I copied it from Infinite Reeee

				public static final boolean RIGHT_ENCODER_REVERSED = false, LEFT_ENCODER_REVERSED = false;
			}

			public static class Shifter {

				public static class Solenoid {
					public static final int FORWARD_PORT = 3;
					public static final int REVERSE_PORT = 1;
				}
			}

			public static class MotionData { // TODO: calibrate this

				public static final ProfilingConfiguration CONFIG = new ProfilingConfiguration(1.0, 1.0, .0005,
						0.05, 0.000, 0.0, .0, 0.2, 0.003, 0, 0, 500);

				public static HashMap<String, ProfilingData> POWER;
				public static HashMap<String, ProfilingData> SPEED;
				public static GearDependentValue<HashMap<String, ProfilingData>> PROF;

				static {

					POWER = new HashMap<>();
					SPEED = new HashMap<>();
					PROF = new GearDependentValue<>(null, null);

					SPEED.put("0.8", new ProfilingData(4.4, 5, 4, 3.5));

					PROF.setValue(Gear.POWER, POWER);
					PROF.setValue(Gear.SPEED, SPEED);
				}

			}


		}

		public static class Intake {

			public static class Motors {
				public static final int ROLLER_PORT = 6;
				public static final boolean IS_REVERSED = true;
			}

			public static class Solenoid {
				public static final int FORWARD_PORT = 2;
				public static final int REVERSE_PORT = 0;
			}

			public static final double POWER = 0.7;
			public static final double REVERSE_POWER = -0.7;
		}

		public static class Shooter {
			public static class ShooterMotor {
				public static final int PORT_LEADER = 7; /*,
						PORT_FOLLOWER = 2;*/
				public static final boolean LEADER_INVERTED = true;  /*,
						FOLLOWER_INVERTED = false;*/


				public static final Dataset RPM_TO_POWER = new Dataset(2);

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
				public static final double EPSILON = 120;
				public static  final double RPM = 3350;
				public static final PIDObject pid = new PIDObject(0.0001, 0.0000003, 0);
				public static final double iZone = 400;
			}
		}

		public static class Funnel {
			public static class FunnelMotor {
				public static final int MOTOR_PORT = 5;
				public static final boolean IS_REVERSED = true;
			}

			public static final double POWER = 0.7;
			public static final double REVERSE_POWER = -0.7;
			public static final int MACRO_SWITCH_PORT = 0;
		}
		public static class Climb {
			public static class SafetyZones {
				public static final double RAIL_SAFETY = 0.003;
				public static final double RAIL_ABSOLUTE_SAFETY = 0;//0.025;
				public static final double SAFETY_LOC = 0.6;
				public static final double LOWEST_ANGLE = 0.25;
				public static final double HIGHEST_ANGLE = Math.PI / 2 - 0.3;
				public static final double TURN_SAFETY = 0.05;
				public static final double TURN_ABSOLUTE_SAFETY = 0.02;
				public static final double BATTERY_SAFETY_ANG = Math.toRadians(51.5); // Math.toDegrees(51.5)
				public static final double BATTERY_SAFETY_LOC = 0.45;
			}
			public static class ClimbMotors {
				public static final int RAIL_MOTOR_PORT = 9;
				public static final boolean RAIL_MOTOR_REVERSED = false;
				public static final double RAIL_MOTOR_TICKS_PER_METER = 14240;
				public static final double RAIL_LENGTH = 0.88;
				public static final double START_LOCATION = 0.623;
				public static final int TURNING_MOTOR_PORT = 11;
				public static final boolean TURNING_MOTOR_REVERSED = false;
				public static final double TURNING_MOTOR_TICKS_PER_RADIAN = 2139;
				public static final double START_ANGLE = 0.279;

			}

			public static class ClimbConstants {
				public static class Rotation {
					public static final double kp = 0.3 / Math.PI * 2;
					public static final double kp_static = 0.2 / Math.PI * 2;
					public static final double ff = 0.2;
					public static final double ff_static = -0.05;

					public static final double RADIANS_TO_SECOND_BAR = Math.toRadians(39);
					public static final double RADIANS_TO_TRAVERSAL = Math.toRadians(0); //TODO: change this
					public static final double RADIANS_TO_MID_GAME = Math.toRadians(75);
					public static final double EPSILON = 0.01;
				}
				
				public static class Rail {
					public static final double kp = 3; //10
					public static final double ff = 0.2;
					public static final double METERS_TO_SECOND_BAR = 0.0;
					public static final double METERS_TO_TRAVERSAL = 0.0;
					public static final double METERS_TO_MID_GAME = 0.41;
					public static final double EPSILON = 0.001;
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

	}
}