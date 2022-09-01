package edu.greenblitz.pegasus.commands.swerve;

public class MoveLin extends SwerveCommand{
	
	private double angle;
	private double power;
	
	public MoveLin(double angleInRotations, double power){
		this.angle = angleInRotations;
		this.power = power;
	}
	
	@Override
	public void execute() {
		swerve.moveChassisLin(angle,power);
	}
}
