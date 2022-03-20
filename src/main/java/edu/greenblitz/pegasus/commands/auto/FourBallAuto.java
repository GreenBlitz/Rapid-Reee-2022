package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbMoveToPosition;
import edu.greenblitz.pegasus.commands.climb.ClimbState;
import edu.greenblitz.pegasus.commands.climb.ToggleClimbPosition;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.commands.shooter.ShooterCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.greenblitz.motion.pid.PIDObject;

public class FourBallAuto extends ParallelCommandGroup {
	private static final double FIRST_DISTANCE = -1.524;
	private static final double SECOND_DISTANCE = 0.0;
	private static final double ANGLE = Math.toRadians(15);

	private static final PIDObject LIN_OBJECT = new PIDObject(0.5,0,0,0);
	public static final PIDObject LIN_OBJECT_ANG = new PIDObject(0.1,0.0000001,0,0);
	private static final PIDObject ANG_OBJECT = new PIDObject(0.4,0,0,0);


	public FourBallAuto(){
		addCommands(
				//new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 5100),
				new SequentialCommandGroup(
					new ToSpeed(),
					new ExtendRoller(),
					new ParallelCommandGroup(
							new MoveFunnelUntilClick(),
							new RunRoller(),
//							new ClimbMoveToPosition(ClimbState.MID_GAME),
							new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, FIRST_DISTANCE)
					),
					new DoubleShoot(5100),
					new MoveAngleByPID(ANG_OBJECT, ANGLE),
					new ParallelCommandGroup(
							new MoveFunnelUntilClick(),
							new RunRoller(),
							new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, SECOND_DISTANCE)
					),
					new ParallelCommandGroup(
							new MoveBallUntilClick(),
							new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, SECOND_DISTANCE)
					),
					new MoveAngleByPID(ANG_OBJECT, -ANGLE),
					new DoubleShoot(5100)
				)
		);
	}
}
