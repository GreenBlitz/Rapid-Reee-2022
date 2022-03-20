package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveAngleByPID extends ChassisCommand {
	private double originalAngleTarget;
	private double angleTarget;
	private double angleCycle;
	private CollapsingPIDController anglePID;

	private static final double EPSILON = Math.toRadians(3);

	public MoveAngleByPID(PIDObject pid, double angleTarget) {
		this.originalAngleTarget = angleTarget;

		anglePID = new CollapsingPIDController(pid, 0);
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < EPSILON);

		chassis.putNumber("p", anglePID.getPidObject().getKp());
		chassis.putNumber("i", anglePID.getPidObject().getKi());
		chassis.putNumber("d", anglePID.getPidObject().getKd());
	}

	public double bestErrorCycle(double angleTargetA, double angleTargetB, double curr) {
		return Math.abs(angleTargetA - curr) < Math.abs(angleTargetB - curr) ? angleTargetA : angleTargetB; //I am asaf i love ternaries
	}

	@Override
	public void initialize() {
		angleCycle = chassis.getRawAngle() - chassis.getRawAngle() % (Math.PI * 2);
		angleTarget = bestErrorCycle(originalAngleTarget, originalAngleTarget + Math.PI * 2, chassis.getRawAngle() - angleCycle);
		angleTarget = bestErrorCycle(angleTarget, originalAngleTarget - Math.PI * 2, chassis.getRawAngle() - angleCycle);
		anglePID.configure(chassis.getRawAngle() - angleCycle, angleTarget, -0.5, 0.5, 0);
		double p = chassis.getNumber("p", anglePID.getPidObject().getKp());
		double i = chassis.getNumber("i", anglePID.getPidObject().getKi());
		double d = chassis.getNumber("d", anglePID.getPidObject().getKd());
		chassis.putNumber("target", angleTarget);
		PIDObject pid = new PIDObject(p, i, d, 0, 1);
		anglePID.setPidObject(pid);
	}

	public void execute() {
		double p = chassis.getNumber("p", anglePID.getPidObject().getKp());
		double i = chassis.getNumber("i", anglePID.getPidObject().getKi());
		double d = chassis.getNumber("d", anglePID.getPidObject().getKd());
		chassis.putNumber("curr", chassis.getRawAngle() - angleCycle);
		PIDObject pid = new PIDObject(p, i, d, 0, 1);
		anglePID.setPidObject(pid);
		double currentAngle = Chassis.getInstance().getRawAngle() - angleCycle;
		double anglePower = anglePID.calculatePID(currentAngle);
		chassis.arcadeDrive(0, anglePower);
	}

	@Override
	public void end(boolean interrupted) {
		chassis.arcadeDrive(0, 0);
	}

	@Override
	public boolean isFinished() {
		return anglePID.isFinished(chassis.getRawAngle() - angleCycle);
	}
}
