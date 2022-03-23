package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.ClimbMoveToPosition;
import edu.greenblitz.pegasus.commands.climb.ClimbState;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbMotors.MID_START_ANGLE;

public class FourBallAuto extends SequentialCommandGroup {
	private static final double FIRST_DISTANCE = -1.45;
	private static final double DISTANCE_TO_SHOOT = 1.4;
	private static final double RPM_SHOOTING = 3600;

	private static final double ANGLE_TO_THIRD_BALL = Math.toRadians(7);
	
	public static final PIDObject LIN_OBJECT = new PIDObject(0.5, 0, 0.25, 0);
	public static final PIDObject LIN_OBJECT_ANG = new PIDObject(0.1, 0.0000001, 0, 0);
	public static final PIDObject ANG_OBJECT = new PIDObject(0.4, 0, 0, 0);
	
	private long tStart;
	
	
	public FourBallAuto() {
		addCommands(
				new ExtendRoller(),
				new WaitCommand(0.4),
				// Go and collect first ball
				new ParallelCommandGroup(
					new ParallelDeadlineGroup(
							new ParallelCommandGroup(
									new ToSpeed(),
									new MoveFunnelUntilClick(),
									new SequentialCommandGroup(
											new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, FIRST_DISTANCE),
											new WaitCommand(0.2)
									)
							),
							new RunRoller(),
							new SequentialCommandGroup(
									new WaitCommand(0.5),
									new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING)
							)
					),
					new SequentialCommandGroup(
						new MoveRailToPosition(0.613),
						new MoveTurningToAngle(MID_START_ANGLE),
						new ClimbMoveToPosition(ClimbState.MID_GAME)
					)
				),

				// Go back
				new ParallelDeadlineGroup(
						new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, DISTANCE_TO_SHOOT),
						new RunRoller(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING)
				),

				new ToPower(),
				new ParallelRaceGroup(
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING),
						new MoveAngleByPID(ANG_OBJECT, -Math.toRadians(15))
				),

				//Shoot
				new DoubleShoot(RPM_SHOOTING),

				//Turn to third ball
				new MoveAngleByPID(ANG_OBJECT, ANGLE_TO_THIRD_BALL),
				new ToSpeed(),

				//Go to Third Ball
				new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -5),
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(
								new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -0.8, 0.2),
								new WaitCommand(1)
						),
						new MoveFunnelUntilClick(),
						new RunRoller()
				),

				//Go back
				new ParallelDeadlineGroup(
						new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, 4.5),
						new RunRoller().withTimeout(1),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING)
				),
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(
								new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, 0.3, 0.2),
								new WaitCommand(0.2)
						),
						new SequentialCommandGroup(
								new WaitCommand(0.5),
								new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, 4400)
						)
				),
				new ParallelDeadlineGroup(
						new MoveAngleByPID(ANG_OBJECT, -Math.toRadians(14)),
						new ToPower(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING)
				),
				new DoubleShoot(RPM_SHOOTING)
		);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		this.tStart = System.currentTimeMillis();
		
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		
		SmartDashboard.putNumber("time elapsed", (System.currentTimeMillis() - this.tStart) / 1000.0);
	}
}
