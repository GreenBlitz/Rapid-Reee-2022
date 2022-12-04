package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.subsystems.Limelight;

import java.util.function.DoubleSupplier;

public class MoveByVisionSupplier extends CombineJoystickMovement {
	public MoveByVisionSupplier(boolean isSlow) {
		super(isSlow, new AngPIDSupplier(new VisionTargetSupplier()));
	}
}
