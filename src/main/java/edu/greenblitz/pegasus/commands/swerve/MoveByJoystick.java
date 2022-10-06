package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Mat;

public class MoveByJoystick extends SwerveCommand {
	private final double maxSpeed;
	private final SmartJoystick joystick;
	
	public MoveByJoystick(SmartJoystick joystick, double maxSpeed) {
		this.joystick = joystick;
		this.maxSpeed = maxSpeed;
	}
	
	@Override
	public void initialize() {
		swerve.configPID(RobotMap.Pegasus.Swerve.angPID, RobotMap.Pegasus.Swerve.linPID);
	}
	
	@Override
	public void execute() {
		double x = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
		double y = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		double angle = Math.atan2(y, x) * -1 + Math.PI / 2;
		double amplitude = Math.hypot(x, y) * this.maxSpeed;
//		SmartDashboard.putNumber("pow", amplitude);
//		SmartDashboard.putNumber("x", x);
//		SmartDashboard.putNumber("y", y);
//		SmartDashboard.putNumber("badJoystickAngle", Math.toDegrees(badAngle));
//		SmartDashboard.putNumber("goodJoystickAngle", Math.toDegrees(angle));
		SmartDashboard.putNumber("Angle", swerve.getTarget(SwerveChassis.Module.FRONT_RIGHT));
//	 	SmartDashboard.putNumber("amplitude", amplitude);
//		SmartDashboard.putNumber("curr angle",(swerve.getAngle(SwerveChassis.Module.BACK_RIGHT)));
		
		if (x == 0 && y == 0) {
			swerve.stop();
			return;
		}
		
		swerve.moveChassisLin(angle, amplitude);
	}
	
	
}

