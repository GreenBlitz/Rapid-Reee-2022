package edu.greenblitz.pegasus.commands.swerve;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetRotPower extends SwerveCommand{
	double power;
	public SetRotPower(double power){
		this.power = power;
	}
	
	@Override
	public void execute() {
		ModuleTest.getInstance().setRotPower(power);
		SmartDashboard.putNumber("lamprey",ModuleTest.getInstance().getLampreyAngle());
		SmartDashboard.putNumber("neo",ModuleTest.getInstance().getMotorAngle());
	}
	
	@Override
	public void end(boolean interrupted) {
		ModuleTest.getInstance().setRotPower(0);
	}
}
