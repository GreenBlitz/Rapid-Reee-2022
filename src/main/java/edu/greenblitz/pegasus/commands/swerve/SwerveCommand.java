package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
import edu.greenblitz.gblib.motors.brushless.SparkMax.SparkMaxFactory;
import edu.greenblitz.gblib.subsystems.swerve.SwerveModule;

public abstract class SwerveCommand extends GBCommand {
	protected ModuleTest moduleTest;
	
	public SwerveCommand() {
		moduleTest = ModuleTest.getInstance();
		require(moduleTest);
	}
	
}
