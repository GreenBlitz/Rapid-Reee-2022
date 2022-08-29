///*
//package edu.greenblitz.pegasus.commands.swerve;
//
//import edu.greenblitz.gblib.base.GBCommand;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class CalibrateMaxMin extends SwerveCommand {
//	private double power;
//	private double maxVal;
//	private double minVal;
//	public CalibrateMaxMin(double power){
//		this.power = power;
//	}
//
//	@Override
//	public void initialize() {
//		maxVal = moduleTest.getLampreyAngle();
//		minVal = moduleTest.getLampreyAngle();
//	}
//
//	@Override
//	public void execute() {
//		moduleTest.setRotpower(power);
//		maxVal = Math.max(maxVal,moduleTest.getLampreyAngle());
//		minVal = Math.min(minVal,moduleTest.getLampreyAngle());
//		SmartDashboard.putNumber("max",maxVal);
//		SmartDashboard.putNumber("min",minVal);
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		moduleTest.setRotpower(0);
//	}
//}
//*/
