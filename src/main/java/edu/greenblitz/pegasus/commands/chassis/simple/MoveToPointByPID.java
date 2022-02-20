<<<<<<< HEAD:src/main/java/edu/greenblitz/pegasus/commands/chassis/simple/MoveToPointByPID.java
package edu.greenblitz.pegasus.commands.chassis.simple;

import edu.greenblitz.pegasus.commands.chassis.turns.TurnToAngleByPID;
import edu.greenblitz.pegasus.subsystems.Chassis;
=======
package edu.greenblitz.icarus.commands.auto;

import edu.greenblitz.icarus.commands.chassis.turns.TurnToAngleByPID;
import edu.greenblitz.icarus.subsystems.Chassis;
>>>>>>> b092291 (?-started work on ShootByRPM):src/main/java/edu/greenblitz/pegasus/commands/auto/MoveToPointByPID.java
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.greenblitz.motion.base.Point;

public class MoveToPointByPID extends SequentialCommandGroup {
	Point startPos;
	Point targetPos;
	
	public MoveToPointByPID(Point targetPos) {
		this.targetPos = targetPos;
		this.startPos = Chassis.getInstance().getLocation();
		double angleTarget = Math.atan((targetPos.getX()- startPos.getX())/ (targetPos.getY()- startPos.getY()));
		double dist = Point.dist(startPos,targetPos);
		addCommands(new TurnToAngleByPID(angleTarget),new MoveStraightByPID(dist));
	}
}
