package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.indexing.HandleBalls;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.greenblitz.pegasus.subsystems.Chassis;
public class TwoBallAuto extends SequentialCommandGroup {

	public TwoBallAuto(){
		addCommands(
				new InstantCommand(new ToggleClimbPosition()),
				new ParallelCommandGroup(
						new MoveSimpleByPID(2),
						new HandleBalls()
				),
				new MoveSimpleByPID(4),
				new DoubleShoot()
		);
	}

}
