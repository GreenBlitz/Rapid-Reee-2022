package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.geometry.Transform3d;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonTrackedTarget;


public class Limelight extends GBSubsystem {
	private static Limelight instance;
	private PhotonCamera camera;
	private Limelight(){
		camera = new PhotonCamera("photonvision");
	}

	public static Limelight getInstance() {
		if (instance == null) {
			instance = new Limelight();
		}
		return instance;
	}


	public double getYawTarget() {
		var result = camera.getLatestResult();
		if (!result.hasTargets()){return 0;}
		PhotonTrackedTarget target = result.getBestTarget();
		return GBMath.modulo(Math.toRadians(target.getYaw()),2 * Math.PI);
	}
	
	/**
	 * it is minus because photon vision is inverted
	 * @return the curr angle minus the target angle
	 */
	public double fieldRelativeTargetYaw(){
		return GBMath.modulo(SwerveChassis.getInstance().getChassisAngle() - getYawTarget(), 2* Math.PI);
	}
	
	/**
	 *
	 * @return an array of three dimensions [x - forward , y - left, z - up]
	 */
	public Transform3d targetPos(){
		var result = camera.getLatestResult();
		if (!result.hasTargets()){return new Transform3d();}
		Transform3d target = result.getBestTarget().getBestCameraToTarget();
		return target;
	}
	
	public boolean FindTarget(){
		return camera.getLatestResult().hasTargets();
	}

}





