package edu.greenblitz.pegasus.subsystems;


import edu.greenblitz.gblib.gear.Gear;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is in charge of the shifter subsystem of the robot.
 * This subsystem includes a DoubleSolenoid.
 * It is important to note that this subsystem is very reliant on the Chassis subsystem, as it changes the gear ratio of that subsystem.
 *
 * @see Chassis
 * @see DoubleSolenoid
 */

public class Shifter extends GBSubsystem {
	
	private static Shifter instance;
	
	private DoubleSolenoid piston;
	
	
	/**
	 * This constructor constructs the piston.
	 */
	private Shifter() {
		piston = new DoubleSolenoid(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID,
				RobotMap.Pegasus.Pneumatics.PCM.PCM_TYPE,
				RobotMap.Pegasus.Chassis.Shifter.Solenoid.FORWARD_PORT,
				RobotMap.Pegasus.Chassis.Shifter.Solenoid.REVERSE_PORT);
	}
	
	/**
	 * This function creates a new instance of this class.
	 */
	public static void init() {
		if (instance == null) {
			instance = new Shifter();
		}
		
	}
	
	/**
	 * This function returns an instance of the class as long as it isn't null.
	 *
	 * @return The current instance of the class
	 */
	public static Shifter getInstance() {
		init();
		return instance;
	}
	
	/**
	 * This function sets the state of the piston based on the value received.
	 *
	 * @param isPower A value based off of the Gear enum. This value is then set as the state the piston is in.
	 */
	public void setShift(boolean isPower) {
		Chassis.getInstance().changeGear();
		Gear.getInstance().setState(isPower);
		piston.set(isPower?
				RobotMap.Pegasus.Chassis.Shifter.POWER_VALUE :
				RobotMap.Pegasus.Chassis.Shifter.SPEED_VALUE);
}
	
	@Override
	public void periodic() {
		super.periodic();
		SmartDashboard.putString("is Power" ,Boolean.toString(Gear.getInstance().getState()));
	}
	
	public void toggleShift() {
		setShift(!Gear.getInstance().getState());
	}

}
