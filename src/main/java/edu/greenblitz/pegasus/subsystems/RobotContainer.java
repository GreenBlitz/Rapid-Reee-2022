package edu.greenblitz.pegasus.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.gblib.gyro.PigeonGyro;
import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
//import edu.greenblitz.gblib.subsystems.Chassis.Chassis;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.compressor.HandleCompressor;

public class RobotContainer {
	private static RobotContainer instance;
	//subsystems
	private Climb climb;
	private Funnel funnel;
	private Indexing indexing;
	private Intake intake;
	private Pneumatics pneumatics;
	private Shifter shifter;
	private Shooter shooter;
	//private Chassis chassis;
	private SwerveChassis swerve;
	
	public static RobotContainer getInstance() {
		if (instance == null) {
			instance = new RobotContainer();
//			instance.initDefaultCommands();
		}
		return instance;
	}
	
	private RobotContainer() {
		//this.swerve = new SwerveChassis(
				//new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 8, 14, 3, 4012, 10 ),
				//new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 7, 16, 2, 4012, 10 ),
				//new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 11, 13, 1, 4012, 10 ),
//				new SwerveModule(new SparkMaxFactory(), new TalonSRXFactory(), 10, 15, 0, 4012, 10 ),
				//new PigeonGyro(new PigeonIMU(1)), 50, 50);
		
	}
	protected void initDefaultCommands(){
		pneumatics.setDefaultCommand(new HandleCompressor());


	}
	
	
	public Climb getClimb() {
		return climb;
	}
	
	public Funnel getFunnel() {
		return funnel;
	}
	
	public Indexing getIndexing() {
		return indexing;
	}
	
	public Intake getIntake() {
		return intake;
	}
	
	public Pneumatics getPneumatics() {
		return pneumatics;
	}
	
	public Shifter getShifter() {
		return shifter;
	}
	
	public Shooter getShooter() {
		return shooter;
	}
	
	//public Chassis getChassis() {
		//return chassis;
	//}
	
	public SwerveChassis getSwerve(){return swerve;}
}


