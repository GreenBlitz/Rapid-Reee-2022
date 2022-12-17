package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.subsystems.Spinner;
import edu.greenblitz.pegasus.utils.commands.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SpinnerCommand extends GBCommand{
	
	@Override
	public void initialize() {
		SmartDashboard.putNumber("Spinner power",0.1);
	}
	
	@Override
	public void execute() {
		Spinner.getInstance().set(SmartDashboard.getNumber("Spinner power",0.1));
	}
	
	@Override
	public void end(boolean interrupted) {
		Spinner.getInstance().stop();
	}
}
