package edu.greenblitz.pegasus.commands.chassis.turns;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class TurnToAngleByPID extends ChassisCommand { //TODO: might be buggy, further testing required
	
	private double angleTarget;
	private CollapsingPIDController anglePID;
	static private PIDObject defaultPIDObject = new PIDObject(0,0,0);
	static private double defaultThresh = 0;
	static private double defaultTolerance = 0;
	public TurnToAngleByPID(double angleTarget) {
		this.angleTarget = angleTarget;
		
		anglePID = new CollapsingPIDController(defaultPIDObject, defaultThresh);
		anglePID.configure(chassis.getAngle(), angleTarget, -0.2, 0.2, 0);
		SmartDashboard.putNumber("angle target", chassis.getAngle());
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < defaultTolerance);
	}
	
	public void execute() {
		double currentAngle = Chassis.getInstance().getAngle();
		chassis.arcadeDrive(0, anglePID.calculatePID(currentAngle));
	}
	
	@Override
	public void end(boolean interrupted) {
		chassis.arcadeDrive(0,0);
	}
	
	@Override
	public boolean isFinished() {
		return anglePID.isFinished(chassis.getAngle());
	}
}
