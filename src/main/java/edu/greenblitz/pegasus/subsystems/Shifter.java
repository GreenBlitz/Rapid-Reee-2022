package edu.greenblitz.pegasus.subsystems;


import edu.greenblitz.gblib.gear.Gear;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is in charge of the shifter subsystem of the robot.
 * This subsystem includes a DoubleSolenoid.
 * It is important to note that this subsystem is very reliant on the Chassis subsystem, as it changes the gear ratio of that subsystem.
 *
 * @see DoubleSolenoid
 */

public class Shifter extends GBSubsystem {


	private final DoubleSolenoid piston;


	/**
	 * This constructor constructs the piston.
	 */
	private Shifter() {
		piston = new DoubleSolenoid(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID,
				RobotMap.Pegasus.Pneumatics.PCM.PCM_TYPE,
				RobotMap.Pegasus.Chassis.Shifter.Solenoid.FORWARD_PORT,
				RobotMap.Pegasus.Chassis.Shifter.Solenoid.REVERSE_PORT);
	}

	private static Shifter instance;

	public static Shifter getInstance(){
		if (instance == null){
			instance = new Shifter();
		}
		return instance;
	}

	/**
	 * This function sets the state of the piston based on the value received.
	 *
	 * @param isPower A value based off of the Gear enum. This value is then set as the state the piston is in.
	 */
	public void setShift(boolean isPower) {
		Gear.getInstance().setState(isPower);
		piston.set(isPower ?
				RobotMap.Pegasus.Chassis.Shifter.POWER_VALUE :
				RobotMap.Pegasus.Chassis.Shifter.SPEED_VALUE);
	}

	@Override
	public void periodic() {
		super.periodic();
		SmartDashboard.putString("is Power", Boolean.toString(Gear.getInstance().getState()));
	}

	public void toggleShift() {
		setShift(!Gear.getInstance().getState());
	}

}
