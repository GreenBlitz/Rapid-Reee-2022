package edu.greenblitz.icarus;

import edu.greenblitz.gblib.gears.GearDependentValue;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.greenblitz.motion.interpolation.Dataset;

public class RobotMap {
	public static class Icarus {
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
		}

		public static class Intake{
			public static final PneumaticsModuleType PCM = PneumaticsModuleType.CTREPCM; //TODO unchecked need to find out correct one

			public static class Motors{
				public static final int ROLLER_PORT = 5;
				public static final boolean IS_REVERSED = false;
			}

			public static class Solenoid{
				public static final int FORWARD_LEFT = 0;
				public static final int REVERSE_LEFT = 0;
				public static final int FORWARD_RIGHT = 1;
				public static final int REVERSE_RIGHT = 1;
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
				public static final int PORT_LEADER = 1,
						PORT_FOLLOWER = 2;
				public static final boolean LEADER_INVERTED = false,
						FOLLOWER_INVERTED = false;


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
				public static final int MOTOR_PORT = 6;
			}
		}

		public static class ComplexClimb {
			public static class ComplexClimbMotor {
				public static final int HOOK_MOTOR_PORT = 8;
				public static final boolean HOOK_MOTOR_REVERSED = false;
				public static final GearDependentValue<Double> HOOK_MOTOR_TICKS_PER_METER = new GearDependentValue<>(0.1,0.1);
				public static final int TURNING_MOTOR_PORT = 6;
				public static final boolean TURNING_MOTOR_REVERSED = false;
				public static final GearDependentValue<Double> TURNING_MOTOR_TICKS_PER_METER = new GearDependentValue<>(0.1,0.1);
			}
		}

	}
}
