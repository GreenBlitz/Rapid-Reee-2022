package edu.greenblitz.pegasus.commands.chassis.localizer;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.Localizer;

public class LocalizerCommand extends GBCommand {
	private Chassis chassis;
	private Localizer localizer;
	
	@Override
	public void initialize() {
		chassis = Chassis.getInstance();
		chassis.resetGyro();
		localizer = Localizer.getInstance();
		localizer.configure(chassis.getWheelDistance(), 0, 0);
	}
	
	@Override
	public void execute() {
		localizer.update(chassis.getLeftMeters(), chassis.getRightMeters());//, chassis.getAngle());
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
}
