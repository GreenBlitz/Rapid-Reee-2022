package edu.greenblitz.pegasus.commands.swerve;

public class MoveModuleLinAndAng extends RotateToAngle {
	
	private double linPower;
	
	public MoveModuleLinAndAng(double angTarget, double linPower) {
		super(angTarget);
		this.linPower = linPower;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		ModuleTest.getInstance().setLinPower(linPower);
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		ModuleTest.getInstance().setLinPower(0);
	}
}
