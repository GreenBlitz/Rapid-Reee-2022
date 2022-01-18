package edu.greenblitz.icarus;

public class RobotMap {
	
	public static class Icarus {
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
		}
		
		public static class Chassis {
			public static class Motor {
				public static final int LEFT_VICTOR = 2,
						RIGHT_VICTOR = 3,
						LEFT_TALON = 1,
						RIGHT_TALON = 4;
			}
		}

		public static class Intake{
			public static final int PCM = -1;

			public static class Motors{
				public static final int ROLLER_PORT = -1;
				public static final boolean IS_REVERSED = false;
			}

			public static class Solenoid{
				public static final int FORWARD_LEFT = -1;
				public static final int REVERSE_LEFT = -1;
				public static final int FORWARD_RIGHT = -1;
				public static final int REVERSE_RIGHT = -1;
			}
		}


	}
}
