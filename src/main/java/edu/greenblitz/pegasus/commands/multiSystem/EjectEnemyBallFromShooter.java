package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.indexing.WaitTillBallExit;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class EjectEnemyBallFromShooter extends SequentialCommandGroup {
	public static final double OUT_RPM = 1600;

	public EjectEnemyBallFromShooter() {
		addCommands(
				new MoveBallUntilClick(),
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(
								new WaitTillBallExit(),
								new WaitCommand(0.5)
						),
						new InsertIntoShooter(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, OUT_RPM)
				)
		);
	}

	@Override
	public void end(boolean interrupted) {
		RobotContainer.getInstance().getShooter().setSpeedByPID(0);
	}
}
