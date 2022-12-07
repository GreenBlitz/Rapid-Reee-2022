package edu.greenblitz.pegasus.subsystems.swerve;

import edu.greenblitz.pegasus.utils.PIDObject;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface SwerveModule {
	void setModuleState(SwerveModuleState moduleState);

	void rotateToAngle(double angle);

	double getModuleAngle();

	double getCurrentVelocity();


	void resetEncoderToValue(double angle) //todo combine both into same overload
	;

	void resetEncoderToValue();

	void configLinPID(PIDObject pidObject);

	void configAnglePID(PIDObject pidObject);

	void setLinSpeed(double speed);

	void stop();

	double getTargetAngle();

	double getTargetVel();

	SwerveModuleState getModuleState();

	double getAbsoluteEncoderValue();

	void setRotPowerOnlyForCalibrations(double power);
}
