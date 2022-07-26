package edu.greenblitz.pegasus.utils;

import edu.wpi.first.wpilibj.DigitalInput;

// This is GBSubsystem for periodic and the dashboard. this isn't a real subsystem.
public class DigitalInputMap extends GBSubsystem {

    private static final int NUMBER_OF_PORTS = 10;

    private static DigitalInputMap instance;
    private DigitalInput[] digitalInputs;

    public static DigitalInputMap getInstance() {
        if (instance == null) {
            instance = new DigitalInputMap();
            instance.register();
        }
        return instance;
    }

    private DigitalInputMap() {

        digitalInputs = new DigitalInput[NUMBER_OF_PORTS];
        for (int i = 0; i < NUMBER_OF_PORTS; i++) {
            digitalInputs[i] = new DigitalInput(i);
        }

    }

    public DigitalInput getDigitalInput(int port) {
        return digitalInputs[port];
    }

    public boolean getValue(int port) {
        return digitalInputs[port].get();
    }

    @Override
    public void periodic() {
        for (int i = 0; i < NUMBER_OF_PORTS; i++) {
            putBoolean("Input #" + i, getValue(i));
        }
    }
}
