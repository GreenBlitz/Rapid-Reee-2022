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
    }
}
