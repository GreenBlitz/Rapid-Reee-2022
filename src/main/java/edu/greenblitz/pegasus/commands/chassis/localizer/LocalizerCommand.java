//package edu.greenblitz.pegasus.commands.chassis.localizer;
//
//import edu.greenblitz.gblib.base.GBCommand;
//import edu.greenblitz.gblib.subsystems.Chassis.Chassis;
//import edu.greenblitz.pegasus.subsystems.RobotContainer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import org.greenblitz.motion.Localizer;
//
//public class LocalizerCommand extends GBCommand {
//	private Chassis chassis;
//	private Localizer localizer;
//
//	@Override
//	public void initialize() {
//		chassis = RobotContainer.getInstance().getChassis();
//		chassis.resetGyro();
//		localizer = Localizer.getInstance();
//		localizer.configure(chassis.getWheelDistance(), 0, 0);
//		localizer.reset(chassis.getLeftMeters(), chassis.getRightMeters());
//	}
//
//	@Override
//	public void execute() {
//		localizer.update(chassis.getLeftMeters(), chassis.getRightMeters(), chassis.getAngle());
//		SmartDashboard.putString("Location", localizer.getLocation().toString());
//	}
//
//	@Override
//	public void end(boolean interrupted) {
//		super.end(interrupted);
//	}
//
//	@Override
//	public boolean isFinished() {
//		return false;
//	}
//}
