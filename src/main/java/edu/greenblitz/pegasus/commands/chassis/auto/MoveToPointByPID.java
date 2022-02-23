package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.chassis.turns.TurnToAngleByPID;
import edu.greenblitz.pegasus.subsystems.Chassis;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.greenblitz.motion.base.Point;

public class MoveToPointByPID extends SequentialCommandGroup {
	Point startPos;
	Point targetPos;
	
	public MoveToPointByPID(Point targetPos, boolean isReverse) {
		this.targetPos = targetPos;
		this.startPos = Chassis.getInstance().getLocation();
		double angleTarget = Math.atan((targetPos.getX()- startPos.getX())/ (targetPos.getY()- startPos.getY()));
		if((targetPos.getY()- startPos.getY() < 0) ^ isReverse){
			angleTarget+=Math.PI;
		}
		double dist = Point.dist(startPos,targetPos);
		addCommands(new TurnToAngleByPID(angleTarget),new MoveSimpleByPID(isReverse ? -dist : dist));
	}

	public MoveToPointByPID(Point targetPos) {
		this(targetPos, false);
	}
}
