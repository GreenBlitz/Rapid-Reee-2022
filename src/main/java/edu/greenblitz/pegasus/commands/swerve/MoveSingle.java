package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;

public class MoveSingle extends SwerveCommand{
	
	private double power;
	private double targetAngle;
	private SwerveChassis.Module module;
	
	public MoveSingle(SwerveChassis.Module module, double targetAngleInRotations, double linPower){
		this.power = linPower;
		this.targetAngle = targetAngleInRotations;
		this.module = module;
	}
	
	@Override
	public void execute() {
		swerve.moveSingleModule(module,targetAngle,power);
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.moveSingleModule(module,targetAngle,0);
	}
}
