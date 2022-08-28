package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class ModuleTest extends SwerveModule implements Subsystem {
	 private static ModuleTest instance;

	 private ModuleTest(){
		super(1, new SparkMaxFactory().withGearRatio(6), new TalonSRXFactory(), 7, 16, 2);
	 }
	 
	 public static ModuleTest getInstance(){
		 if (instance == null){
			 instance = new ModuleTest();
		 }
		 return instance;
	 }

}
