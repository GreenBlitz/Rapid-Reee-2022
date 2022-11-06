package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import java.util.Arrays;
import java.util.function.Supplier;

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
		double[] arr = {-1, -1};
		double[] location = new double[2];
		double angle = 1;
		NetworkTableEntry loc = NetworkTableInstance.getDefault().getTable("limelight").getEntry("llpython");
		if (loc != null) {
			System.out.println(Arrays.toString(loc.getDoubleArray(arr)));
			location = loc.getDoubleArray(arr);
			angle = Math.atan2(location[0],location[1]);
		}
		return angle;
	}

}


