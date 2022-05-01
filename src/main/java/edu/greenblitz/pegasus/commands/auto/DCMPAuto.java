package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbMoveToPosition;
import edu.greenblitz.pegasus.commands.climb.ClimbState;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.StopShooter;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;

public class DCMPAuto extends SequentialCommandGroup {

	private static final PIDObject LIN_OBJECT = new PIDObject(0.4, 0, 0.25, 0);
	private static final PIDObject LIN_OBJECT_ANG = new PIDObject(0.3, 0, 0, 0); //0.2, 0, 0

	public DCMPAuto(){
		addCommands(
				new ToSpeed(),
				new ParallelCommandGroup(
						new SequentialCommandGroup(
								new ParallelDeadlineGroup(
										new ParallelRaceGroup(
												new SequentialCommandGroup(
														new MoveRailToPosition(0.613),
														new MoveTurningToAngle(MID_START_ANGLE),
														new ClimbMoveToPosition(ClimbState.MID_GAME)
												),
												new WaitCommand(3)

										),
										new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM),
										new MoveBallUntilClick()
										),
										new ParallelRaceGroup(
												new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.RPM),
												new InsertIntoShooter()
										)
						),
						new WaitCommand(5)
				),
				new WaitCommand(0.2),
				new StopShooter(),
				new MoveLinearWithoutPID(LIN_OBJECT_ANG, -0.5, 0.3),
				new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -2.5, 0.3, -Math.toRadians(35))

		);
}
}
