package edu.greenblitz.icarus.commands.chassis.driver;

import edu.greenblitz.icarus.utils.VisionMaster;
import edu.greenblitz.gblib.hid.SmartJoystick;

public class ArcadeDriveUntilVision extends ArcadeDrive {
    public ArcadeDriveUntilVision(SmartJoystick joystick) {
        super(joystick);
    }

    @Override
    public boolean isFinished() {
        return VisionMaster.getInstance().isLastDataValid();
    }
}
