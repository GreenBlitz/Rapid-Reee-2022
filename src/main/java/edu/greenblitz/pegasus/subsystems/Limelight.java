package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

<<<<<<< HEAD
=======
import java.util.Arrays;
import java.util.function.Supplier;
>>>>>>> 7fbcedf (Amir - added getlocation function)

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

<<<<<<< HEAD

	public double getLocation() {
		double arr = -1;
		NetworkTableEntry loc = NetworkTableInstance.getDefault().getTable("limelight").getEntry("angle");
		double angle = loc.getDouble(1);
		return angle;
	}

}

=======
	public double[] getLocation() {
		double[] arr = {-1, -1};
		NetworkTableEntry loc = NetworkTableInstance.getDefault().getTable("limelight").getEntry("llpython");
		if (loc != null) {
			System.out.println(Arrays.toString(loc.getDoubleArray(arr)));
		}
		return new double[5];
	}

	}
>>>>>>> 7fbcedf (Amir - added getlocation function)
