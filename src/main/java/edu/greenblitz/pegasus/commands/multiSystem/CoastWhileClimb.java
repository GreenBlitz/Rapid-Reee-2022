//package edu.greenblitz.pegasus.commands.multiSystem;
//
//import edu.greenblitz.gblib.base.GBCommand;
//import edu.greenblitz.pegasus.subsystems.Chassis;
//
//public class CoastWhileClimb extends GBCommand {
//
//	public CoastWhileClimb() {
//	}
//
//	@Override
//	public void initialize() {
//		super.initialize();
//		Chassis.getInstance().toCoast();
////		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		Chassis.getInstance().toBrake();
////		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
//	}
//}
