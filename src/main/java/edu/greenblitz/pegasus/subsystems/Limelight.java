package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;



public class Limelight extends GBSubsystem {
	private static Limelight instance;

	public static void init() {
		instance = new Limelight();
	}

	public static Limelight getInstance() {
		if (instance == null) {
			init();
		}
		return instance;
	}


	public double getLocation() {
		double arr = -1;
		NetworkTableEntry loc = NetworkTableInstance.getDefault().getTable("limelight").getEntry("angle");
		double angle = loc.getDouble(1);
		return angle;
	}

}





