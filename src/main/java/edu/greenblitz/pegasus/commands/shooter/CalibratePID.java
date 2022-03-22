package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.RobotMap;
import org.greenblitz.motion.pid.PIDObject;

import java.util.ArrayDeque;
import java.util.Queue;

public class CalibratePID extends ShooterByRPM {
	
	Queue<Double> lastValues = new ArrayDeque<>(50);
	
	public CalibratePID(PIDObject obj, double target) {
		super(obj, target);
		shooter.putNumber("p", obj.getKp());
		shooter.putNumber("i", obj.getKi());
		shooter.putNumber("d", obj.getKd());
		shooter.putNumber("iZone", 0);
		shooter.putNumber("target", target);
	}


	public CalibratePID(PIDObject obj, double iZone, double target) {
		super(obj, iZone, target);
		shooter.putNumber("p", obj.getKp());
		shooter.putNumber("i", obj.getKi());
		shooter.putNumber("d", obj.getKd());
		shooter.putNumber("iZone", iZone);
		shooter.putNumber("target", target);
	}
	
	@Override
	public void execute() {
		double p = shooter.getNumber("p", obj.getKp());
		double i = shooter.getNumber("i", obj.getKi());
		double d = shooter.getNumber("d", obj.getKd());
		double iZone = 	shooter.getNumber("iZone", shooter.getPIDController().getIZone());
		this.target = shooter.getNumber("target", this.target);
		double ff = RobotMap.Pegasus.Shooter.ShooterMotor.RPM_TO_POWER.linearlyInterpolate(target)[0]/target;
		obj = new PIDObject(p, i, d, ff);
		shooter.setPIDConsts(obj, iZone);
		super.execute();
		lastValues.add(Math.abs(shooter.getShooterSpeed()));
		if (lastValues.size() > 50){
			lastValues.remove();
		}
		double mean = 0;
		for (double value : lastValues) {
			mean += value;
		}
		mean /= lastValues.size();
		shooter.putNumber("mean", mean);
		double sumSquareErrors = 0;
		for(double value: lastValues){
			sumSquareErrors += Math.pow((value - mean), 2);
		}
		double meanSquareErrors = sumSquareErrors / lastValues.size();
		double standardDeviation = Math.sqrt(meanSquareErrors);
		shooter.putNumber("standard deviation", standardDeviation);
		
		
		
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		shooter.setSpeedByPID(0);
		shooter.getPIDController().setIAccum(0.0);
	}
}
