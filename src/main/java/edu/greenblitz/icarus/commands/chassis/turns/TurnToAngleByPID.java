package edu.greenblitz.icarus.commands.chassis.turns;

import edu.greenblitz.icarus.commands.chassis.ChassisCommand;
import edu.greenblitz.icarus.subsystems.Chassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class TurnToAngleByPID extends ChassisCommand {
	
	private double angleTarget;
	private CollapsingPIDController anglePID;
	
	public TurnToAngleByPID(CollapsingPIDController anglePID, double angleTarget, double thresh, double tolerance, PIDObject PIDObject) {
		this.angleTarget = angleTarget;
		this.anglePID = anglePID;
		anglePID = new CollapsingPIDController(PIDObject, thresh);
		anglePID.configure(chassis.getAngle(), angleTarget, -0.2, 0.2, 0);
		SmartDashboard.putNumber("angle target", chassis.getAngle());
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < tolerance);
	}
	
	
	public TurnToAngleByPID(CollapsingPIDController anglePID, double angleTarget, double thresh, double tolerance) {
		this(anglePID, angleTarget, thresh, tolerance, new PIDObject(0.0, 0.0, 0.0));
	}
	
	@Override
	public void initialize() {
	
	}
	
	public void execute() {
		double currentAngle = Chassis.getInstance().getAngle();
		chassis.arcadeDrive(0, anglePID.calculatePID(currentAngle));
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
	}
	
	@Override
	public boolean isFinished() {
		return super.isFinished();
	}
}
