package edu.greenblitz.pegasus.commands.climb.Rail;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;

public class RailByJoystick extends RailCommand{
	
	private  SmartJoystick joystick;
	
	public RailByJoystick(SmartJoystick joystick){
		super();
		this.joystick = joystick;
	}
	
	@Override
	public void execute() {
		super.execute();
		if (joystick.L1.get()){
			if (joystick.R1.get()){
				Climb.getInstance().resetRailMotorTicks();
			}
			double railMotorPower = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
			climb.unsafeMoveRailMotor(railMotorPower);
		}
		else{
			double railMotorPower = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
			climb.safeMoveRailMotor(railMotorPower);
			
		}
	}
}
