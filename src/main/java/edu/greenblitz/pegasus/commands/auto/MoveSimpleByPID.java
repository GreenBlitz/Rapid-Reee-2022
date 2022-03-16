package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveSimpleByPID extends ChassisCommand{
		private Point startPos;
		private double distanceTarget;
		private CollapsingPIDController distancePID;
		static private PIDObject defaultPIDObject = new PIDObject(0.5,0.0000,0);
		static private double defaultThresh = 0.3;
		static private double defaultTolerance = 0.1;
		public MoveSimpleByPID(double distanceTarget) {
			startPos = chassis.getLocation();
			this.distanceTarget = distanceTarget;
			distancePID = new CollapsingPIDController(defaultPIDObject, defaultThresh);
			distancePID.configure(Point.dist(startPos,chassis.getLocation()), distanceTarget, -0.2, 0.2, 0);
			distancePID.setTolerance((goal, current) -> Math.abs(goal - current) < defaultTolerance);

			chassis.putNumber("p", distancePID.getPidObject().getKp());
			chassis.putNumber("i", distancePID.getPidObject().getKp());
			chassis.putNumber("d", distancePID.getPidObject().getKp());
		}

	@Override
	public void initialize() {

		double p  = chassis.getNumber("p", distancePID.getPidObject().getKp());
		double i  = chassis.getNumber("i", distancePID.getPidObject().getKi());
		double d  = chassis.getNumber("d", distancePID.getPidObject().getKd());
		distancePID.getPidObject().setKp(p);
		distancePID.getPidObject().setKp(i);
		distancePID.getPidObject().setKp(d);
	}

	@Override
	public void execute() {
			double currentDistance = Point.dist(startPos,chassis.getLocation());
			chassis.arcadeDrive(distancePID.calculatePID(currentDistance), 0);
		

		}
		
		@Override
		public void end(boolean interrupted) {
			chassis.arcadeDrive(0,0);
		}
		
		@Override
		public boolean isFinished() {
			return distancePID.isFinished(Point.dist(startPos,chassis.getLocation()));
		}
	}
	

