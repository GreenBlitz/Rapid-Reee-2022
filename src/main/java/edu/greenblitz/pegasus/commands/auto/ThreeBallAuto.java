package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.*;

import static edu.greenblitz.pegasus.commands.auto.FourBallAuto.LIN_OBJECT;
import static edu.greenblitz.pegasus.commands.auto.FourBallAuto.LIN_OBJECT_ANG;

public class ThreeBallAuto extends SequentialCommandGroup {
	private static final double FIRST_LINEAR_DISTANCE = 1;
	private static final double DISTANCE_TO_SHOOTING = 0.87;
	private static final double ANGLE_TO_SHOOTING = Math.toRadians(21);
	private static final double GO_BACK_DISTANCE = 0.25;
	private static final double DISTANCE_TO_THIRD_BALL = 1.3;
	private static final double ANGLE_TO_THIRD_BALL = Math.toRadians(90);
	private static final double FINAL_DISTANCE = 1.2;

	public ThreeBallAuto(){
		addCommands(
				// Open roller and prepare to go to the first ball
				new ExtendRoller(),
				new ClimbToMidGame(),
				new ToSpeed(),

				// Go to the first
				new ParallelDeadlineGroup(
						new ParallelCommandGroup(
								new MoveFunnelUntilClick(),
								new SequentialCommandGroup(
										new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -FIRST_LINEAR_DISTANCE), //first you go back
										new WaitCommand(0.2)
								)
						),
						new RunRoller(),
						new SequentialCommandGroup(
								new WaitCommand(0.5),
								new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
						)
				),

				// Start to go back
				new ParallelDeadlineGroup(
						new ParallelCommandGroup(
								new MoveFunnelUntilClick(),
								new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, FIRST_LINEAR_DISTANCE)
						),
						new RunRoller(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
				),

				// Go to shooting position
				new ParallelRaceGroup(
						new WaitCommand(1),
						new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, DISTANCE_TO_SHOOTING, ANGLE_TO_SHOOTING),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
				),

				// Robot.move();
				new ParallelRaceGroup(
					new RobotDotMove(0.1),
					new WaitCommand(0.5),
					new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
				),

				// Shoot!
				new DoubleShoot(),

				// Start to go to third ball
				new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, -GO_BACK_DISTANCE),

				// Go to new angle for the third ball
				new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, -DISTANCE_TO_THIRD_BALL, ANGLE_TO_THIRD_BALL),

				// Final distance
				new ParallelDeadlineGroup(
						new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, -FINAL_DISTANCE),
						new RunRoller(),
						new MoveFunnelUntilClick()
				),

				// Start to go back
				new ParallelDeadlineGroup(
						new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, FINAL_DISTANCE),
						new RunRoller(),
						new MoveFunnelUntilClick()
				),

				// Turn back
				new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, DISTANCE_TO_THIRD_BALL, ANGLE_TO_SHOOTING),

				// Go to shooting
				new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, GO_BACK_DISTANCE),

				// Shoot!
				new DoubleShoot()
		);
	}
}
