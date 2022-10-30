package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;

public class MoveToTarget extends GBCommand {
	public MoveToTarget(double targetAngle){
		SwerveChassis.getInstance().moveChassisLin();
	}
}
