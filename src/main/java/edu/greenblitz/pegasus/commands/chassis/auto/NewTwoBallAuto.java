package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class NewTwoBallAuto extends SequentialCommandGroup {

	public NewTwoBallAuto(){
		addCommands(new ParallelCommandGroup(new MoveSimpleByPID(-2),new HandleBalls()),new DoubleShoot());
	}
}
