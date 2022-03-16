package edu.greenblitz.pegasus.commands.chassis.turns;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class TurnToAngleByPID extends ChassisCommand { //TODO: might be buggy, further testing required
	
	
	private double originalAngleTarget;
	private double angleTarget;
	private double angleCycle;
	private CollapsingPIDController anglePID;
	static private PIDObject defaultPIDObject = new PIDObject(0.4, 0.0, 0, 0, 0, 0.0);
	static private double defaultThresh = 0.025;
	static private double defaultTolerance = 0.05;
	
	
	public TurnToAngleByPID(double angleTarget) {
		this.originalAngleTarget = angleTarget;
		
		anglePID = new CollapsingPIDController(defaultPIDObject, defaultThresh);
		anglePID.setTolerance((goal, current) -> Math.abs(goal - current) < defaultTolerance);
		
		chassis.putNumber("p", anglePID.getPidObject().getKp());
		chassis.putNumber("i", anglePID.getPidObject().getKi());
		chassis.putNumber("d", anglePID.getPidObject().getKd());
		chassis.putNumber("iZone", anglePID.getPidObject().getIZone());
	}
	
	public double bestErrorCycle(double angleTargetA, double angleTargetB, double curr) {
		return Math.abs(angleTargetA - curr) < Math.abs(angleTargetB - curr) ? angleTargetA : angleTargetB; //I am asaf i love ternaries
	}
	
	@Override
	public void initialize() {
		angleCycle = chassis.getRawAngle() - chassis.getRawAngle() % (Math.PI*2);
		angleTarget = bestErrorCycle(originalAngleTarget, originalAngleTarget + Math.PI * 2, chassis.getRawAngle() - angleCycle);
		angleTarget = bestErrorCycle(angleTarget, originalAngleTarget - Math.PI * 2, chassis.getRawAngle() - angleCycle);
		anglePID.configure(chassis.getRawAngle() - angleCycle, angleTarget, -1, 1, 0);
		double p = chassis.getNumber("p", anglePID.getPidObject().getKp());
		double i = chassis.getNumber("i", anglePID.getPidObject().getKi());
		double d = chassis.getNumber("d", anglePID.getPidObject().getKd());
		double iZone = chassis.getNumber("iZone", 0);
		chassis.putNumber("target", angleTarget);
		PIDObject pid = new PIDObject(p, i, d, 0, 1, iZone);
		anglePID.setPidObject(pid);
	}
	
	public void execute() {
		double p = chassis.getNumber("p", anglePID.getPidObject().getKp());
		double i = chassis.getNumber("i", anglePID.getPidObject().getKi());
		double d = chassis.getNumber("d", anglePID.getPidObject().getKd());
		double iZone = chassis.getNumber("iZone", anglePID.getPidObject().getIZone());
		chassis.putNumber("curr", chassis.getRawAngle() - angleCycle);
		PIDObject pid = new PIDObject(p, i, d, 0, 1, iZone);
		anglePID.setPidObject(pid);
		double currentAngle = Chassis.getInstance().getRawAngle() - angleCycle;
		double anglePower = anglePID.calculatePID(currentAngle);
		System.out.println(anglePower);
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
