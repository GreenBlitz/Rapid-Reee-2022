package edu.greenblitz.pegasus.commands.chassis.turns;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class TurnToAngleByPID extends ChassisCommand { //TODO: might be buggy, further testing required

	static private final PIDObject defaultPIDObject = new PIDObject(0, 0, 0);
	static private final double defaultThresh = 0;
	static private final double defaultTolerance = 0;
	private final double angleTarget;
	private final CollapsingPIDController anglePID;

	public TurnToAngleByPID(double angleTarget) {
		this.angleTarget = angleTarget;

		anglePID = new CollapsingPIDController(defaultPIDObject, defaultThresh);
		anglePID.configure(chassis.getAngle(), angleTarget, -0.2, 0.2, 0);
		SmartDashboard.putNumber("angle target", chassis.getAngle());
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < defaultTolerance);
	}

	public void execute() {
		double currentAngle = chassis.getAngle();
		chassis.arcadeDrive(0, anglePID.calculatePID(currentAngle));
	}

	@Override
	public void end(boolean interrupted) {
		chassis.arcadeDrive(0, 0);
	}

	@Override
	public boolean isFinished() {
		return anglePID.isFinished(chassis.getAngle());
	}
}
