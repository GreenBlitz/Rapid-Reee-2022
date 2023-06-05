package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.wpi.first.math.geometry.Translation2d;

import java.util.function.DoubleSupplier;

public class DEBUGRotateSwerveToMimicTurret extends CombineJoystickMovement {


    public DEBUGRotateSwerveToMimicTurret(boolean isSlow, DoubleSupplier angSupplier) {
        super(isSlow, new AngPIDSupplier(new Translation2d(RobotMap.GameField.SHOOTING_TARGET_POINT.getX(), RobotMap.GameField.SHOOTING_TARGET_POINT.getY())));
    }
}
