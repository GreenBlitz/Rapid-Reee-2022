package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbMoveToPosition;
import edu.greenblitz.pegasus.commands.climb.ClimbState;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.*;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;

public class TwoBallAuto extends SequentialCommandGroup {
	public TwoBallAuto(){
		addCommands(
				new ToSpeed(),
				new ParallelCommandGroup(
						new ExtendRoller(),
						new SequentialCommandGroup(
								new MoveRailToPosition(0.613),
								new MoveTurningToAngle(MID_START_ANGLE),
								new ClimbMoveToPosition(ClimbState.MID_GAME)
						),
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new RobotDotMove(-0.1),
						new RunRoller(),
						new WaitCommand(2)
				),
				new ParallelRaceGroup(
						new RunRoller(),
						new RobotDotMove(0.15),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 2900),
						new WaitCommand(5)
				),
				new DoubleShoot(),
				new ParallelCommandGroup(
						new RunRoller(),
						new RunFunnel(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 2900)
				)
		);
	}
}