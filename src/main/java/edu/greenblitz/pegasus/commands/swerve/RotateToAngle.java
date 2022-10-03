package edu.greenblitz.pegasus.commands.swerve;

public class RotateToAngle extends SwerveCommand{
	private double ang;
	
	public RotateToAngle(double angInRads){
		this.ang = angInRads;
	}
	
	public void execute(){
		swerve.moveChassisLin(ang,0);
	}
}
