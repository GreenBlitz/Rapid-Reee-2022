package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;

public class ClimbByJoysticks extends ClimbCommand{

	private SmartJoystick joystick;

	public ClimbByJoysticks(SmartJoystick joystick){
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		if (joystick.L1.get()){
			if (joystick.R3.get()){
				Climb.getInstance().resetTurningMotorTicks();
			}
			if (joystick.L3.get()){
				Climb.getInstance().resetRailMotorTicks();
			}
			double railMotorPower = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
			climb.unsafeMoveRailMotor(railMotorPower);

			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.unsafeMoveTurningMotor(turningMotorPower*0.4);
		}
		else{
		double railMotorPower = -joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.safeMoveRailMotor(railMotorPower);

		double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
		climb.safeMoveTurningMotor(turningMotorPower*0.4);
		}
	}

	@Override
	public void end(boolean interrupted) {
	}
}
