package edu.greenblitz.pegasus.utils.swerveKinematics;

import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveModule;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import javax.management.ValueExp;

public class SwerveKinematics {
	static SwerveChassis chassis= SwerveChassis.getInstance();

	private static Vector transform;
	private static double angleVelocity;

	public static void setTransform(Vector transForm) {transform = transForm;}
	public static void setAngleVelocity(double AngleVelocity) {angleVelocity = AngleVelocity;}
	public double getAngleVelocity(){return this.angleVelocity;}
	public Vector getTransform(){return this.transform;}


	public static void Move(){


		SwerveModule modules[] = new SwerveModule[4];

		modules[0] = chassis.getModule(SwerveChassis.Module.FRONT_RIGHT);
		modules[1] = chassis.getModule(SwerveChassis.Module.FRONT_LEFT);
		modules[2] = chassis.getModule(SwerveChassis.Module.BACK_RIGHT);
		modules[3] = chassis.getModule(SwerveChassis.Module.BACK_LEFT);

		for (int i = 0; i < modules.length; i++) {
			modules[i].setTransform(transform);
			modules[i].setAngularVelocity(angleVelocity);
			modules[i].update();
		}
	}



}
