package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.RobotMap;
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
	public double fieldRelativeTargetYaw(){
		return GBMath.modulo(SwerveChassis.getInstance().getChassisAngle() - getYawTarget(), 2* Math.PI);
	}
	
	public boolean FindTarget(){
		return camera.getLatestResult().hasTargets();
	}

}





