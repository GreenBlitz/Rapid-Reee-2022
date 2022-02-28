package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class ShooterByRPM extends ShootByConstant{
	
	private double rpm;
	
	public ShooterByRPM(double rpm){
		super(RobotMap.Pegasus.Shooter.ShooterMotor.RPM_TO_POWER.linearlyInterpolate(rpm)[0]);
		shooter.putNumber("RPM", rpm);
		this.rpm = rpm;
	}
	
	@Override
	public void execute() {
		power = RobotMap.Pegasus.Shooter.ShooterMotor.RPM_TO_POWER.linearlyInterpolate(shooter.getNumber("RPM" , rpm))[0];
		super.execute();
	}
}
