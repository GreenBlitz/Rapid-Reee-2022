package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveByJoystick extends SwerveCommand {
	private SmartJoystick joystick;
	private final double maxPower = 0.3;
	
	public MoveByJoystick(SmartJoystick joystick) {
		this.joystick = joystick;
	}
	
	@Override
	public void initialize() {
	swerve.configPID(new PIDObject().withKp(0.05).withMaxPower(0.2));
	}
	
	@Override
	public void execute() {
		double x = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X) * maxPower;
		double y = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y) * maxPower;
		double angle =  Math.atan(y / x) / (Math.PI * 2) + (x < 0 ? 0.5 : 0) ;
		double amplitude = Math.hypot(x, y);
		SmartDashboard.putNumber("x", x);
		SmartDashboard.putNumber("y", y);
		SmartDashboard.putNumber("angle", angle);
		SmartDashboard.putNumber("amplitude", amplitude);
		
		if (x == 0 && y == 0) {
			swerve.brakeModules(SwerveChassis.Module.BACK_LEFT, SwerveChassis.Module.BACK_RIGHT, SwerveChassis.Module.FRONT_LEFT, SwerveChassis.Module.FRONT_RIGHT);
			System.out.println("op1");
			return;
		}
		
		if (x == 0 && y > 0) {
			swerve.moveChassisLin(0.25, amplitude);
			System.out.println("op2");
			return;
		}
		if (x == 0 && y < 0) {
			swerve.moveChassisLin(-0.25, amplitude);
			System.out.println("op3");
			return;
		}
		
		swerve.moveChassisLin(angle, amplitude);
		System.out.println("op4");

//		if(y  == 0 && x == 0) throw new Exception("brake the engines in this case");
//		else swerve.moveChassisLin( Math.atan(y/x)/ (Math.PI *2) , Math.hypot(x,y));
		//TODO needs to break engine if  no input
	}
	
	
}
