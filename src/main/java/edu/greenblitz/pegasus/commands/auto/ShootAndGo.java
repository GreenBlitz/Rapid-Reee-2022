package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ShootAndGo extends SequentialCommandGroup {
	public ShootAndGo(double movementTime) {
		addCommands(
				new ParallelRaceGroup(
						new WaitCommand(1),
						new ShootByConstant(0.7)
				),
				new ParallelRaceGroup(
						new WaitCommand(4),
						new ShootByConstant(0.7),
						new RunFunnel()
				),
				new ParallelRaceGroup(
						new WaitCommand(movementTime),
						new RobotDotMove()
				)
		);
	}
}
