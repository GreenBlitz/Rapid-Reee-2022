package edu.greenblitz.robotName;

import edu.greenblitz.gblib.hid.SmartJoystick;

public class OI {
    private static OI instance;

    private SmartJoystick mainJoystick;
    private SmartJoystick secondJoystick;

    private OI() {
        mainJoystick = new SmartJoystick(RobotMap.BigRodika.Joystick.MAIN);
        secondJoystick = new SmartJoystick(RobotMap.BigRodika.Joystick.SECOND);
    }

    public static OI getInstance() {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public SmartJoystick getSecondJoystick() {
        return secondJoystick;
    }
}
