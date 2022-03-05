package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.indexing.WaitTillBallExit;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;


public class EjectEnemyBallFromShooter extends SequentialCommandGroup {
	
	public static final double OUT_POWER = 0.3;
	
	public EjectEnemyBallFromShooter(){
		addCommands(
				new MoveBallUntilClick(),
				new ParallelRaceGroup(
						new WaitTillBallExit(), // TODO: 05/03/2022  exchange this for wait till macroswitch off 
						new RunFunnel(),
						new ShootByConstant(OUT_POWER)
				)
		);
	}
}
