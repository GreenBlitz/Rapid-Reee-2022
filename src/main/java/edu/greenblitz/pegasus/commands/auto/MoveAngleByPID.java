package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveAngleByPID extends ChassisCommand {
	private static final double EPSILON = Math.toRadians(2);
	private final double originalAngleTarget;
	private final boolean sendData;
	private final CollapsingPIDController anglePID;
	private double angleTarget;
	private double angleCycle;
	private double ff;

	public MoveAngleByPID(PIDObject pid, double angleTarget, boolean sendData) {
		this.originalAngleTarget = angleTarget;
		this.sendData = sendData;

		anglePID = new CollapsingPIDController(pid, 0);
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < EPSILON);
		ff = pid.getKf();
		if (sendData) {
			SmartDashboard.putNumber("p", anglePID.getPidObject().getKp());
			SmartDashboard.putNumber("i", anglePID.getPidObject().getKi());
			SmartDashboard.putNumber("d", anglePID.getPidObject().getKd());
			SmartDashboard.putNumber("ff", anglePID.getPidObject().getKf());

		}
	}

	public MoveAngleByPID(PIDObject pid, double angleTarget) {
		this(pid, angleTarget, false);
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
		if (sendData) {
			double p = SmartDashboard.getNumber("p", anglePID.getPidObject().getKp());
			double i = SmartDashboard.getNumber("i", anglePID.getPidObject().getKi());
			double d = SmartDashboard.getNumber("d", anglePID.getPidObject().getKd());
			ff = SmartDashboard.getNumber("ff", anglePID.getPidObject().getKf());
//			chassis.putNumber("target", angleTarget);
			PIDObject pid = new PIDObject(p, i, d, 0, 1);
			anglePID.setPidObject(pid);
		}
	}

	public void execute() {
		if (sendData) {
			double p = SmartDashboard.getNumber("p", anglePID.getPidObject().getKp());
			double i = SmartDashboard.getNumber("i", anglePID.getPidObject().getKi());
			double d = SmartDashboard.getNumber("d", anglePID.getPidObject().getKd());
			ff = SmartDashboard.getNumber("ff", anglePID.getPidObject().getKf());
			SmartDashboard.putNumber("curr", chassis.getRawAngle() - angleCycle);
			PIDObject pid = new PIDObject(p, i, d, 0, 1);
			anglePID.setPidObject(pid);
		}
		double currentAngle = chassis.getRawAngle() - angleCycle;
		double anglePower = anglePID.calculatePID(currentAngle);
		if (anglePower != 0) {
			anglePower = anglePower + ff * Math.signum(anglePower);
		}
		chassis.arcadeDrive(0, anglePower);
	}

	@Override
	public void end(boolean interrupted) {
		chassis.arcadeDrive(0, 0);
	}

	@Override
	public boolean isFinished() {
		return (anglePID.isFinished(chassis.getRawAngle() - angleCycle));
	}
}
