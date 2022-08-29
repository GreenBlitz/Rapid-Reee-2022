package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;

public class MoveByJoystick extends SwerveCommand{
	private SmartJoystick joystick;
	public MoveByJoystick(SmartJoystick joystick){
		this.joystick = joystick;
	}
	
	@Override
	public void execute() {
		double x = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X);
		double y = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
//		if(y  == 0 && x == 0) throw new Exception("brake the engines in this case");
//		else swerve.moveChassisLin( Math.atan(y/x)/ (Math.PI *2) , Math.hypot(x,y));
		//TODO needs to break engine if  no input
	}
	
	
	
}
