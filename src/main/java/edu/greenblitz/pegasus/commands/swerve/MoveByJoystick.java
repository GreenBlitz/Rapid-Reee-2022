package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveByJoystick extends SwerveCommand {
	private final double maxPower = 0.3;
	private final SmartJoystick joystick;
	private final PIDObject pid;


	public MoveByJoystick(SmartJoystick joystick, PIDObject pidObject) {
		this.joystick = joystick;
		this.pid = pidObject;
	}

	@Override
	public void initialize() {
		swerve.configPID(RobotMap.Pegasus.Swerve.pid);
	}

	@Override
	public void execute() {
		double x = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X) * maxPower;
		double y = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y) * maxPower;
		double angle = Math.atan(y / x) + (x < 0 ? Math.PI : 0);
		double amplitude = Math.hypot(x, y);
//		SmartDashboard.putNumber("pow", amplitude);
//		SmartDashboard.putNumber("x", x);
//		SmartDashboard.putNumber("y", y);
//		SmartDashboard.putNumber("angle", angle);
//		SmartDashboard.putNumber("target", swerve.getTarget(SwerveChassis.Module.BACK_RIGHT));
//		SmartDashboard.putNumber("amplitude", amplitude);
//		SmartDashboard.putNumber("curr angle",(swerve.getAngle(SwerveChassis.Module.BACK_RIGHT)));


		if (x == 0 && y == 0) {
			swerve.stop();
			return;
		}

		if (x == 0 && y > 0) {
			swerve.moveChassisLin(GBMath.modulo(swerve.getAngle(SwerveChassis.Module.BACK_RIGHT),Math.PI  * 2), amplitude);
			return;
		}
		if (x == 0 && y < 0) {
			swerve.moveChassisLin(GBMath.modulo(swerve.getAngle(SwerveChassis.Module.BACK_RIGHT),Math.PI  * 2), amplitude);
			return;
		}

		swerve.moveChassisLin(angle, amplitude);

		if(y  == 0 && x == 0){
			swerve.stop();
		}
		else swerve.moveChassisLin( Math.atan(y/x)/ (Math.PI *2) , Math.hypot(x,y));
	}


}

