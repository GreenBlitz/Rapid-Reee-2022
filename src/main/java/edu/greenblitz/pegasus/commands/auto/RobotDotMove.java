//package edu.greenblitz.pegasus.commands.auto;
//
//
//import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
//
//public class RobotDotMove extends ChassisCommand {
//	private static final double DEF_POWER = -0.1;
//
//	private final double power;
//
//	public RobotDotMove() {
//		this.power = DEF_POWER;
//	}
//
//	public RobotDotMove(double power) {
//		this.power = power;
//	}
//
//	@Override
//	public void execute() {
//		chassis.moveMotors(power, power);
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		chassis.moveMotors(0, 0);
//	}
//}
