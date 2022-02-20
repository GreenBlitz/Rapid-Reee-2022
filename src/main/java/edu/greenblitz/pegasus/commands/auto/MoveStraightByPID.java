package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.pid.CollapsingPIDController;
import org.greenblitz.motion.pid.PIDObject;

public class MoveStraightByPID extends ChassisCommand{
		private Point startPos;
		private double distanceTarget;
		private CollapsingPIDController distancePID;
		static private PIDObject defaultPIDObject = new PIDObject(0,0,0);
		static private double defaultThresh = 0;
		static private double defaultTolerance = 0;
		public MoveStraightByPID(double distanceTarget) {
			startPos = chassis.getLocation();
			this.distanceTarget = distanceTarget;
			distancePID = new CollapsingPIDController(defaultPIDObject, defaultThresh);
			distancePID.configure(Point.dist(startPos,chassis.getLocation()), distanceTarget, -0.2, 0.2, 0);
			SmartDashboard.putNumber("distance target",Point.dist(startPos,chassis.getLocation()));
			distancePID.setTolerance((goal, current) -> Math.abs(goal - current) < defaultTolerance);
		}
		
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
	

