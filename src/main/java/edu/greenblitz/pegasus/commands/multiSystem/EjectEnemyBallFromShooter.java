package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.SlowlyShoot;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class EjectEnemyBallFromShooter extends SequentialCommandGroup {
	public EjectEnemyBallFromShooter(){
		addCommands(
				new MoveBallUntilClick(),
				new ParallelRaceGroup(
						new WaitCommand(1),
						new RunFunnel(),
						new SlowlyShoot()
				)
		);
	}
}
