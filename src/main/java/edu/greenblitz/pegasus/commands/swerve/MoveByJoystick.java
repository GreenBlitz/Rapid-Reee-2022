package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.utils.GBMath;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveByJoystick extends SwerveCommand {
	private final double maxSpeed;
	private final SmartJoystick joystick;

	public MoveByJoystick(SmartJoystick joystick, double maxSpeed) {
		this.joystick = joystick;
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void initialize() {
		swerve.configPID(RobotMap.Pegasus.Swerve.angPID,RobotMap.Pegasus.Swerve.linPID);
	}

	@Override
	public void execute() {
		double x = joystick.getAxisValue(SmartJoystick.Axis.LEFT_X);
		double y = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		double angle = Math.atan(y / x) + (x < 0 ? Math.PI : 0);
		double amplitude = Math.hypot(x, y) * this.maxSpeed;
		SmartDashboard.putNumber("angle fr_right",SwerveChassis.getInstance().getAngle(SwerveChassis.Module.FRONT_RIGHT));
		SmartDashboard.putNumber("angle fr_left",SwerveChassis.getInstance().getAngle(SwerveChassis.Module.FRONT_LEFT));
		SmartDashboard.putNumber("angle back_right",SwerveChassis.getInstance().getAngle(SwerveChassis.Module.BACK_RIGHT));
		SmartDashboard.putNumber("angle back_left",SwerveChassis.getInstance().getAngle(SwerveChassis.Module.BACK_LEFT));
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

	}


}

