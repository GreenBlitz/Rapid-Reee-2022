package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.Chassis.Chassis;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.pegasus.RobotMap;

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
	private Chassis chassis;
	
	public static RobotContainer getInstance() {
		if (instance == null) {
			instance = new RobotContainer();
		}
		return instance;
	}
	
	private RobotContainer() {
		this.climb = new Climb();
		this.funnel = new Funnel();
		this.intake = new Intake();
		this.indexing = new Indexing();
		this.pneumatics = new Pneumatics();
		this.shifter = new Shifter();
		Shooter.create(new SparkMaxFactory(), RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);
		this.shooter = Shooter.getInstance();
		Chassis.create(new SparkMaxFactory(), RobotMap.Pegasus.Chassis.Motors.ports, RobotMap.Pegasus.Chassis.Motors.isInverted, RobotMap.Pegasus.Chassis.WHEEL_DIST);
		this.chassis = Chassis.getInstance();
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
	
	public Chassis getChassis() {
		return chassis;
	}
}


