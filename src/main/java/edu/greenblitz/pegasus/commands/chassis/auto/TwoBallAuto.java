package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TwoBallAuto extends SequentialCommandGroup {
	public TwoBallAuto(){
		addCommands(
				new InstantCommand(new ToggleClimbPosition()),
				new ExtendRoller(),
				new ParallelCommandGroup(
						new MoveSimpleByPID(-2),
						new RunRoller(),
						new MoveBallUntilClick()
				),
				new MoveSimpleByPID(4),
				new DoubleShoot()
		);
	}
}
