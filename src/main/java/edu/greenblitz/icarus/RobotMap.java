package edu.greenblitz.icarus;

import edu.greenblitz.gblib.gears.Gear;
import edu.greenblitz.gblib.gears.GearDependentValue;
import org.greenblitz.motion.interpolation.Dataset;
import org.greenblitz.motion.profiling.ProfilingConfiguration;
import org.greenblitz.motion.profiling.ProfilingData;

import java.util.HashMap;

public class RobotMap {
	public static class Icarus {
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
		}

		public static class Chassis {
			public static final double WHEEL_DIST = 0.0; //very accurate right now

			public static class Motors {
				public static final int RIGHT_LEADER = 1, RIGHT_FOLLOWER_1 = 2, RIGHT_FOLLOWER_2 = -1, LEFT_LEADER = 3, LEFT_FOLLOWER_1 = 4, LEFT_FOLLOWER_2 = -1;
				public static final boolean RIGHT_LEADER_REVERSED = false, RIGHT_FOLLOWER_1_REVERSED = false, RIGHT_FOLLOWER_2_REVERSED = false, LEFT_LEADER_REVERSED = false, LEFT_FOLLOWER_1_REVERSED = false, LEFT_FOLLOWER_2_REVERSED = false;
			}

			public static class Encoders {
				public static final GearDependentValue<Double> NORM_CONST_SPARK = new GearDependentValue<>(2300.0 * 0.64, 1234.0 / 2.0); // TODO: check this, I copied it from Infinite Reeee
				public static final int RIGHT_ENCODER = -1, LEFT_ENCODER = -1;
				public static final boolean RIGHT_ENCODER_REVERSED = false, LEFT_ENCODER_REVERSED = false;
			}

			public static class MotionData { // TODO: calibrate this

				public static final ProfilingConfiguration CONFIG = new ProfilingConfiguration(
						0.85, 1.0, .0005,
						0.8, 0.0, 2.0, .01,
						0.5 * 0, 0, 0, .01, 500);
				public static HashMap<String, ProfilingData> POWER;
				public static HashMap<String, ProfilingData> SPEED;
				public static GearDependentValue<HashMap<String, ProfilingData>> PROF;

				static {

					POWER = new HashMap<>();
					SPEED = new HashMap<>();
					PROF = new GearDependentValue<>(null, null);

					POWER.put("1.0",
							new ProfilingData(2.64, 7, 8, 30));
					POWER.put("0.5",
							new ProfilingData(1.4, 8.4, 4, 10));

					// TODO this is dumb
					POWER.put("0.3",
							new ProfilingData(1.93 * 1.2, 4.6, 4.3, 12.6));


					SPEED.put("0.3",
							new ProfilingData(1.93 * 1.2, 4.6, 4.3, 12.6));

					PROF.setValue(Gear.POWER, POWER);
					PROF.setValue(Gear.SPEED, SPEED);
				}

			}


		}

		public static class Intake {
			public static final int PCM = -1;

			public static class Motors {
				public static final int ROLLER_PORT = -1;
				public static final boolean IS_REVERSED = false;
			}

			public static class Solenoid {
				public static final int FORWARD_LEFT = -1;
				public static final int REVERSE_LEFT = -1;
				public static final int FORWARD_RIGHT = -1;
				public static final int REVERSE_RIGHT = -1;
			}
		}

		public static class Climb {
			public static class Motor {
				public static final int MOTOR = -1;
				public static final boolean MOTOR_REVERSE = false;
				public static final GearDependentValue<Double> MOTOR_TICKS_PER_METER = null;
			}
		}

		public static class Shooter {
			public static class ShooterMotor {
				public static final int PORT_LEADER = 8, PORT_FOLLOWER = 9;
				public static final boolean LEADER_INVERTED = false, FOLLOWER_INVERTED = false;


				public static final Dataset RPM_TO_POWER = new Dataset(2);

				static {
					RPM_TO_POWER.addDatapoint(0, new double[]{0});
					RPM_TO_POWER.addDatapoint(975, new double[]{0.2});
					RPM_TO_POWER.addDatapoint(2062.5, new double[]{0.4});
					RPM_TO_POWER.addDatapoint(3075, new double[]{0.6});
					RPM_TO_POWER.addDatapoint(3960, new double[]{0.8});
				}
				// No fucking idea how much is 1.0, but 0.8 is already very fucking scary
//        rpmToPowerMap.addDatapoint(5500, new double[]{1.0});

			}
		}

		public static class Funnel {
			public static class FunnelMotor {
				public static final int MOTOR_PORT = -1;
			}
		}
	}
}
