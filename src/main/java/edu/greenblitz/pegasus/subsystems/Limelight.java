package edu.greenblitz.pegasus.subsystems;

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

	public double[] getLocation() {
		double[] arr = {-1, -1, -1};
		NetworkTableEntry loc = NetworkTableInstance.getDefault().getTable("limelight").getEntry("llpython");
		if (loc != null) {
			if (loc.getDoubleArray(arr) != null)
				return (loc.getDoubleArray(arr));
			else
				System.out.println("Inner null");
		} else {
			System.out.println("Null Object");
		}
		return new double[5];
	}

}
