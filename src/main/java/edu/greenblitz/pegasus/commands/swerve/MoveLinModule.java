package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;

public class MoveLinModule extends SwerveCommand{
	
	double power;
	
	public MoveLinModule(double power){
		this.power = power;
	}
	
	@Override
	public void execute() {
		ModuleTest.getInstance().setLinPower(power);
	}
	
	@Override
	public void end(boolean interrupted) {
		ModuleTest.getInstance().setLinPower(0);
	}
}
