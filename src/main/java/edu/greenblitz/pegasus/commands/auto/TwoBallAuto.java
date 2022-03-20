package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.*;

public class TwoBallAuto extends SequentialCommandGroup {
	public TwoBallAuto(){
		addCommands(
				new ToPower(),
				new ParallelCommandGroup(
						new ExtendRoller(),
						//new InstantCommand(new ToggleClimbPosition()), TODO: Uncomment me
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new RobotDotMove(-0.52),
						new WaitCommand(0.5),
						new RunRoller()
				),
				new ParallelRaceGroup(
						new RobotDotMove(0.52),
						new WaitCommand(0.5),
						new RunRoller()
				),
				new DoubleShoot(3600)
		);
	}
}
