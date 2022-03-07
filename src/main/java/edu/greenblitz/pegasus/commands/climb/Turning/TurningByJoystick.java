package edu.greenblitz.pegasus.commands.climb.Turning;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;

public class TurningByJoystick extends TurningCommand {
	private SmartJoystick joystick;
	
	public TurningByJoystick(SmartJoystick joystick){
		super();
		this.joystick = joystick;
	}
	
	@Override
	public void execute() {
		if (joystick.L1.get()){
			if (joystick.R3.get()){
				Climb.getInstance().resetTurningMotorTicks();
			}
			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.unsafeMoveTurningMotor(turningMotorPower*0.3);
			}
		else{
			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.safeMoveTurningMotor(turningMotorPower*0.3);
		}
		}
}
