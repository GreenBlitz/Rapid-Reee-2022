//package edu.greenblitz.pegasus.commands.swerve;
//
//import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.hid.SmartJoystick;
//import edu.greenblitz.pegasus.utils.PIDObject;
//import edu.greenblitz.pegasus.utils.GBMath;
//
//public class MoveSingleByJoystick extends SwerveCommand {
//	public SmartJoystick joystick;
//
//	public MoveSingleByJoystick(SmartJoystick joystick) {
//		this.joystick = joystick;
//	}
//
//	@Override
//	public void initialize() {
//		ModuleTest.getInstance().configAnglePID(new PIDObject().withKp(0.1).withMaxPower(0.1));
//	}
//
//	@Override
//	public void execute() {
//		double y = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
//		double x = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_X);
//		double angle = GBMath.modulo(Math.atan2(y, x), Math.PI * 2);
//		double power = Math.hypot(x, y);
//		ModuleTest.getInstance().setLinPower(power);
//		if (power != 0) {
//			ModuleTest.getInstance().rotateToAngle(angle);
//		}
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		ModuleTest.getInstance().setLinPower(0);
//	}
//}
//
