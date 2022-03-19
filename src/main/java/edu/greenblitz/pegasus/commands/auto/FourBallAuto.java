package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.greenblitz.motion.pid.PIDObject;

public class FourBallAuto extends SequentialCommandGroup {
	private static final double FIRST_DISTANCE = 0.0;
	private static final double SECOND_DISTANCE = 0.0;
	private static final double ANGLE = 0.0;

	private static final PIDObject LIN_OBJECT = new PIDObject(0,0,0,0);
	private static final PIDObject ANG_OBJECT = new PIDObject(0,0,0,0);


	public FourBallAuto(){
		addCommands(
				new ToPower(),
				new ExtendRoller(),
				new ParallelCommandGroup(
						new MoveFunnelUntilClick(),
						new RunRoller(),
						new InstantCommand(new ToggleClimbPosition()),
						new MoveLinearByPID(LIN_OBJECT, FIRST_DISTANCE)
				),
				new DoubleShoot(5100),
				new MoveAngleByPID(ANG_OBJECT, ANGLE),
				new ParallelCommandGroup(
						new MoveFunnelUntilClick(),
						new RunRoller(),
						new MoveLinearByPID(LIN_OBJECT, SECOND_DISTANCE)
				),
				new ParallelCommandGroup(
						new MoveBallUntilClick(),
						new MoveLinearByPID(LIN_OBJECT, -SECOND_DISTANCE)
				),
				new MoveAngleByPID(ANG_OBJECT, -ANGLE),
				new DoubleShoot(5100)
		);
	}
}
