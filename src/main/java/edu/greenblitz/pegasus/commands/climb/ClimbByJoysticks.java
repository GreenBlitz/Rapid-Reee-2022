package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;
import org.opencv.core.Mat;

public class ClimbByJoysticks extends ClimbCommand{

	private SmartJoystick joystick;

	public ClimbByJoysticks(SmartJoystick joystick){
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		if (joystick.R1.get()){
			if (joystick.B.get()){
				Climb.getInstance().resetTurningMotorTicks();
			}
			if (joystick.A.get()){
				Climb.getInstance().resetRailMotorTicks();
			}
			double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
			climb.unsafeMoveRailMotor(railMotorPower);

			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.unsafeMoveTurningMotor(turningMotorPower*0.4);
			System.out.println("ang: " + (90 - climb.getAng()*360/2/Math.PI));
			System.out.println("loc: " + climb.getLoc()*100);
		}
		else{
		double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.safeMoveRailMotor(railMotorPower);

		double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
		climb.safeMoveTurningMotor(turningMotorPower*0.4);
		System.out.println("ang: " + (90 - climb.getAng()*360/2/Math.PI));
		System.out.println("loc: " + climb.getLoc()*100);
		}
	}

	@Override
	public void end(boolean interrupted) {
	}
}
