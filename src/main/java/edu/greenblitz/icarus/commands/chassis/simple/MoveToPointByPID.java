package edu.greenblitz.icarus.commands.chassis.simple;

import edu.greenblitz.icarus.commands.chassis.ChassisCommand;
import edu.greenblitz.icarus.commands.chassis.turns.TurnToAngleByPID;
import edu.greenblitz.icarus.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.pid.PIDObject;

public class MoveToPointByPID extends SequentialCommandGroup {
	Point startPos;
	Point targetPos;
	
	public MoveToPointByPID(Point targetPos) {
		this.targetPos = targetPos;
		this.startPos = Chassis.getInstance().getLocation();
		double angleTarget = Math.atan((targetPos.getX()- startPos.getX())/ (targetPos.getY()- startPos.getY()));
		double dist = Point.dist(startPos,targetPos);
		addCommands(new TurnToAngleByPID(angleTarget),new MoveSimpleByPID(dist));
	}
}
