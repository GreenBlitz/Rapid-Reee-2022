package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.gblib.threading.ThreadedCommand;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.chassis.profiling.Follow2DProfileCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.State;

import java.util.ArrayList;

public class FourBallAuto extends SequentialCommandGroup {

	public FourBallAuto(double maxPower) {

		//Path for the first 2 balls
		ArrayList<State> firstTwo = new ArrayList<>();
		firstTwo.add(new State(1, 1.78, -1.51, 0, 0)); //start pos
		firstTwo.add(new State(3.1, 2.2, -1.11, 3.6, 4)); // ball 2

		addCommands(new ThreadedCommand(new Follow2DProfileCommand(firstTwo , RobotMap.Pegasus.Chassis.MotionData.CONFIG
				, maxPower , false)));

		//Inorder to shoot, go back in a linear line for 3.13 meter
		addCommands(new MoveToPointByPID(new Point(3.1, 2.2) // currant location at path
				.translate(new Point(0,-3.13).rotate(-1.11)))); // a vector of length 3.13 rotated to back of robot

		//firstTwo.add(new State(0.3, 0.8, -1.11, 3.6, 4));

		//Path for balls 3 and 4
		ArrayList<State> lastTwo = new ArrayList<>();
		lastTwo.add(new State(0.3,0.8,-1.11,0,0)); // shoot pos
		lastTwo.add(new State(7.1,3,-1.11,3.6,4)); // balls 3,4




	}
}
