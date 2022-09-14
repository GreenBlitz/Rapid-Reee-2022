package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;

public class moveSingleModule extends SwerveCommand{
	double angle;
	double power;



	public moveSingleModule(double power, double angle){
		this.angle = angle;
		this.power = power;
		
	}
	
	@Override
	public void initialize() {
//		SwerveModule a = new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 8, 14, 3, 4012, 10);
//		SwerveModule b = new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 10, 15, 0, 4012, 10);
//		SwerveModule c = new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 11, 13, 1, 4012, 10);
//		SwerveModule d = new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 7, 16, 2, 4012, 10);
//		PigeonGyro pigeon = new PigeonGyro (new PigeonIMU(1));
	}
	
	@Override
	public void execute() {
		swerve.moveSingleModule(SwerveChassis.Module.FRONT_RIGHT,angle,power);
		swerve.moveSingleModule(SwerveChassis.Module.FRONT_LEFT,angle,power);
		swerve.moveSingleModule(SwerveChassis.Module.BACK_LEFT,angle,power);
		swerve.moveSingleModule(SwerveChassis.Module.BACK_RIGHT,angle,power);
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.moveSingleModule(SwerveChassis.Module.FRONT_RIGHT,0,0);
		swerve.moveSingleModule(SwerveChassis.Module.FRONT_LEFT,0,0);
		swerve.moveSingleModule(SwerveChassis.Module.BACK_LEFT,0,0);
		swerve.moveSingleModule(SwerveChassis.Module.BACK_RIGHT,0,0);
	}
}
