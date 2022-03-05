package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class NewTwoBallAuto extends SequentialCommandGroup {

	public NewTwoBallAuto() {

		addCommands(
				new ParallelRaceGroup(
						new RobotDotMove(-0.1),
						new WaitCommand(3),
						new RunRoller()
				),
				new ParallelRaceGroup(
						new RobotDotMove(0.1),
						new WaitCommand(6)
				),
				new DoubleShoot()
		);
	}
}
