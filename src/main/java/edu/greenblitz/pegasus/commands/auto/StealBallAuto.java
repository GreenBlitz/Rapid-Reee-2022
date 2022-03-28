package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ExtendFully;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

public class StealBallAuto extends SequentialCommandGroup {
	private static final PIDObject LIN_OBJECT = new PIDObject(0.4, 0, 0.25, 0);
	private static final PIDObject LIN_OBJECT_ANG = new PIDObject(0.3, 0, 0, 0); //0.2, 0, 0

	private static final double FIRST_LINEAR_DISTANCE = 1;
	private static final double DISTANCE_TO_SHOOTING = 0.87;
	private static final double ANGLE_TO_SHOOTING = Math.toRadians(21);
	private static final double GO_BACK_DISTANCE = 0.4;
	private static final double DISTANCE_TO_THIRD_BALL = 1.3;
	private static final double ANGLE_TO_THIRD_BALL = Math.toRadians(90);
	private static final double FINAL_DISTANCE = 1.2;

	public StealBallAuto(){
		addCommands(
				new ToSpeed(),
				new ExtendRoller(),

				// Start to go to third ball
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -GO_BACK_DISTANCE, ANGLE_TO_SHOOTING,0.3),

				// Go to new angle for the third ball
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_THIRD_BALL / 2.0, 0.3),
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_THIRD_BALL, 0.3),

				// Final distance
				new ParallelDeadlineGroup(
						new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -FINAL_DISTANCE, ANGLE_TO_THIRD_BALL, 0.3),
						new RunRoller(),
						new MoveFunnelUntilClick()
				),

				// Start to go back
				new ParallelDeadlineGroup(
						new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, FINAL_DISTANCE, ANGLE_TO_THIRD_BALL, 0.3),
						new RunRoller(),
						new MoveFunnelUntilClick()
				),

				// Turn back
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_THIRD_BALL / 2.0, 0.3),
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_SHOOTING, 0.3),

				// Go to shooting
				new ParallelRaceGroup(
						new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, GO_BACK_DISTANCE),
						new WaitCommand(1)
				),

				// Robot.move();
				new ParallelDeadlineGroup(
						new WaitCommand(0.5),
						new ClimbToMidGame(),
						new RobotDotMove(0.1),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
				),

				// Shoot!
				new ParallelCommandGroup(
						new DoubleShoot(2300),
						new ParallelRaceGroup(
								new WaitCommand(1.5),
								new RobotDotMove(0.1)
						)
				),

				// Go to the first
				new ParallelDeadlineGroup(
						new ParallelCommandGroup(
								new MoveFunnelUntilClick(),
								new SequentialCommandGroup(
										new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -2 * FIRST_LINEAR_DISTANCE), //first you go back
										new WaitCommand(0.2),
										new InstantCommand(() -> System.out.println("Finished moving"))
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
								new MoveLinearWithoutPID(LIN_OBJECT_ANG, FIRST_LINEAR_DISTANCE + DISTANCE_TO_SHOOTING, ANGLE_TO_SHOOTING, 0.3)
						),
						new ParallelDeadlineGroup(
								new WaitCommand(0.5),
								new RunRoller()
						),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
				),

				// Robot.move();
				new ParallelRaceGroup(
						new RobotDotMove(0.1),
						new WaitCommand(0.5),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
				),

				// Shoot!
				new ParallelCommandGroup(
						new DoubleShoot(2300),
						new ParallelRaceGroup(
								new WaitCommand(1.5),
								new RobotDotMove(0.1)
						)
				)
		);
	}
}
