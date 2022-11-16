package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AngPIDSupplier implements DoubleSupplier{
	private DoubleSupplier ang;
	private PIDController chassisPid;

	public AngPIDSupplier(DoubleSupplier ang){
		SmartDashboard.putNumber("kp", 0.1);
		SmartDashboard.putNumber("ki", 0);
		SmartDashboard.putNumber("kd", 0);
		this.ang = ang;
		chassisPid = new PIDController(RobotMap.Pegasus.Swerve.rotationPID.getKp(),RobotMap.Pegasus.Swerve.rotationPID.getKi(),RobotMap.Pegasus.Swerve.rotationPID.getKd());
		chassisPid.enableContinuousInput(0,2*Math.PI); //min and max
	}

	@Override
	public double getAsDouble() {
		double kp = SmartDashboard.getNumber("kp",0.1);
		double kd = SmartDashboard.getNumber("kd",0);
		double ki = SmartDashboard.getNumber("ki",0);
		chassisPid.setPID(kp,ki,kd);
		return Math.max(Math.min(chassisPid.calculate(SwerveChassis.getInstance().getChassisAngle(),ang.getAsDouble()),0.4),-0.4);
	}
}
