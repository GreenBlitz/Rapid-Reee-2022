package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.math.controller.PIDController;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class AngPIDSupplier implements DoubleSupplier{
	private DoubleSupplier ang;
	private PIDController chassisPid;

	public AngPIDSupplier(DoubleSupplier ang){
		this.ang = ang;
		chassisPid = new PIDController(RobotMap.Pegasus.Swerve.rotationPID.getKp(),RobotMap.Pegasus.Swerve.rotationPID.getKi(),RobotMap.Pegasus.Swerve.rotationPID.getKd());
	}

	@Override
	public double getAsDouble() {
		return chassisPid.calculate(SwerveChassis.getInstance().getChassisAngle(),ang.getAsDouble());

	}
}
