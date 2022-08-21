package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.Chassis.Chassis;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;
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
	private Chassis chassis;
	
	public static RobotContainer getInstance() {
		if (instance == null) {
			instance = new RobotContainer();
			instance.initDefaultCommands();
		}
		return instance;
	}
	
	private RobotContainer() {
		this.shooter = new Shooter(new SparkMaxFactory()
				.withInverted(RobotMap.Pegasus.Shooter.ShooterMotor.LEADER_INVERTED)
				.withIdleMode(AbstractMotor.IdleMode.Coast)
				.withCurrentLimit(40)
				.withRampRate(1), RobotMap.Pegasus.Shooter.ShooterMotor.PORT_LEADER);
		this.funnel = new Funnel();
		this.climb = new Climb();
		this.intake = new Intake();
		this.indexing = new Indexing();
		this.pneumatics = new Pneumatics();
		this.shifter = new Shifter();
		this.chassis = new Chassis(new SparkMaxFactory()
				.withCurrentLimit(40), RobotMap.Pegasus.Chassis.Motors.ports, RobotMap.Pegasus.Chassis.Motors.isInverted, RobotMap.Pegasus.Chassis.WHEEL_DIST);
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
	
	public Chassis getChassis() {
		return chassis;
	}
}


