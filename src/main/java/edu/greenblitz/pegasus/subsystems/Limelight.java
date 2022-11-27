package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
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
		PhotonTrackedTarget target = result.getBestTarget();
		if (!camera.getLatestResult().hasTargets()){return 0;}
		return Math.toRadians(target.getYaw());
	}
	public double fieldRelativeTargetYaw(){
		return SwerveChassis.getInstance().getChassisAngle() - getYawTarget();
	}
	
	public boolean FindTarget(){
		return camera.getLatestResult().hasTargets();
	}

}





