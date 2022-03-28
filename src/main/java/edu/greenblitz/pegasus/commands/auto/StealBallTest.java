package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.ToggleRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

public class StealBallTest extends SequentialCommandGroup {
	private static final PIDObject LIN_OBJECT = new PIDObject(0.4, 0, 0.25, 0);
	private static final PIDObject LIN_OBJECT_ANG = new PIDObject(0.3, 0, 0, 0); //0.2, 0, 0

	private static final double FIRST_LINEAR_DISTANCE = 0.5;
	private static final double DISTANCE_TO_SHOOTING = 0.435;
	private static final double ANGLE_TO_SHOOTING = Math.toRadians(21);
	private static final double GO_BACK_DISTANCE = 0.2;
	private static final double DISTANCE_TO_THIRD_BALL = 0.65;
	private static final double ANGLE_TO_THIRD_BALL = Math.toRadians(90);
	private static final double FINAL_DISTANCE = 0.6;
	private static final double MAX_POWER = 0.2;

	public StealBallTest(){
		addCommands(
				// Go to the first
				new ParallelDeadlineGroup(
						new ParallelCommandGroup(
								new ToggleRoller(),
								new ClimbToMidGame(),
								new ToSpeed(),
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
								new MoveLinearWithoutPID(LIN_OBJECT_ANG, FIRST_LINEAR_DISTANCE + DISTANCE_TO_SHOOTING, -ANGLE_TO_SHOOTING, MAX_POWER)
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
				),


				// Start to go to third ball
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -GO_BACK_DISTANCE, ANGLE_TO_SHOOTING,MAX_POWER),

				// Go to new angle for the third ball
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_THIRD_BALL / 2.0, MAX_POWER),
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_THIRD_BALL, MAX_POWER),

				// Final distance
				new ParallelDeadlineGroup(
						new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, -FINAL_DISTANCE, ANGLE_TO_THIRD_BALL, MAX_POWER),
						new RunRoller(),
						new MoveFunnelUntilClick()
				),

				// Start to go back
				new ParallelDeadlineGroup(
						new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, FINAL_DISTANCE, ANGLE_TO_THIRD_BALL, MAX_POWER),
						new RunRoller(),
						new MoveFunnelUntilClick()
				),

				// Turn back
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, DISTANCE_TO_THIRD_BALL / 2.0, ANGLE_TO_THIRD_BALL / 2.0, MAX_POWER),
				new MoveAndPrepShooterWithoutPID(LIN_OBJECT_ANG, DISTANCE_TO_THIRD_BALL / 2.0, -ANGLE_TO_SHOOTING, MAX_POWER),

				// Go to shooting
				new ParallelRaceGroup(
						new MoveAndPrepShooter(LIN_OBJECT, LIN_OBJECT_ANG, GO_BACK_DISTANCE),
						new WaitCommand(1)
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
