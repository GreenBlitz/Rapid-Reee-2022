package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.gears.Gear;
import edu.greenblitz.gblib.gears.GearDependentValue;
import edu.greenblitz.gblib.gears.GlobalGearContainer;
import edu.greenblitz.gblib.sendables.GBDoubleSolenoid;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

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
	
	private GBDoubleSolenoid piston;
	private Gear currentShift = Gear.POWER;
	private GearDependentValue<DoubleSolenoid.Value> shiftValue =
			new GearDependentValue<>(DoubleSolenoid.Value.kForward, DoubleSolenoid.Value.kReverse);
	
	
	/**
	 * This constructor constructs the piston.
	 */
	private Shifter() {
		
		piston = new GBDoubleSolenoid(RobotMap.Pegasus.Chassis.Shifter.PCM,
				RobotMap.Pegasus.Chassis.Shifter.Solenoid.FORWARD,
				RobotMap.Pegasus.Chassis.Shifter.Solenoid.REVERSE);
		
		
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
	 * @param state A value based off of the Gear enum. This value is then set as the state the piston is in.
	 */
	public void setShift(Gear state) {
		currentShift = state;
		Chassis.getInstance().changeGear();
		GlobalGearContainer.getInstance().setGear(state);
		piston.set(state == Gear.POWER ? DoubleSolenoid.Value.kReverse: DoubleSolenoid.Value.kForward);
	}
	
	@Override
	public void periodic() {
		super.periodic();
		putString("Shift", currentShift.name());
	}
	
	public void toggleShift() {
		setShift(getCurrentGear() == Gear.POWER ? Gear.SPEED : Gear.POWER);
	}
	
	/**
	 * This function returns the current state of the piston through the Gear enum.
	 *
	 * @return The state of the piston through the Gear enum
	 */
	public Gear getCurrentGear() {
		return GlobalGearContainer.getInstance().getGear();
	}
}
