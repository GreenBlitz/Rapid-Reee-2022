package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.motors.brushless.SparkMax.GBSparkMax;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.greenblitz.pegasus.RobotMap;

public class ModuleTest extends SwerveModule {
	private static ModuleTest instance;
	
	private ModuleTest() {
		super( new SparkMaxFactory().withGearRatio(6), new TalonSRXFactory(), 10, 15, 0, RobotMap.Pegasus.Swerve.Module2.MAX_LAMPREY_VAL,RobotMap.Pegasus.Swerve.Module2.MIN_LAMPREY_VAL);
	}
	
	public static ModuleTest getInstance() {
		if(instance == null){
			instance = new ModuleTest();
		}
		return instance;
	}
	
	public void SwerveInit(){
		instance.restEncoderByLamprey();
	}
	}


