package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.wpi.first.wpilibj2.command.*;

public class TwoBallAuto extends SequentialCommandGroup {
	public TwoBallAuto(){
		addCommands(
				new ParallelCommandGroup(
						new ExtendRoller(),
						new InstantCommand(new ToggleClimbPosition()),
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new RobotDotMove(-0.1),
						new RunRoller(),
						new WaitCommand(1.5)
				),
				new ParallelRaceGroup(
						new RobotDotMove(0.1),
						new WaitCommand(3)
				),
				new DoubleShoot()
		);
	}
}
