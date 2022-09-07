package edu.greenblitz.pegasus.commands.swerve;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.greenblitz.pegasus.subsystems.RobotContainer;

public class moveSingleModule extends SwerveCommand{
	double angle;
	double power;
	SwerveChassis sc;
	


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
		sc = RobotContainer.getInstance().getSwerve();
	}
	
	@Override
	public void execute() {
		sc.moveSingleModule(SwerveChassis.Module.FRONT_RIGHT,angle,power);
		sc.moveSingleModule(SwerveChassis.Module.FRONT_LEFT,angle,power);
		sc.moveSingleModule(SwerveChassis.Module.BACK_LEFT,angle,power);
		sc.moveSingleModule(SwerveChassis.Module.BACK_RIGHT,angle,power);
	}
	
	@Override
	public void end(boolean interrupted) {
		sc.moveSingleModule(SwerveChassis.Module.FRONT_RIGHT,0,0);
		sc.moveSingleModule(SwerveChassis.Module.FRONT_LEFT,0,0);
		sc.moveSingleModule(SwerveChassis.Module.BACK_LEFT,0,0);
		sc.moveSingleModule(SwerveChassis.Module.BACK_RIGHT,0,0);
	}
}
