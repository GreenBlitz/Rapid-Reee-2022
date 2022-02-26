package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.commands.funnel.ReverseFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRoller;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class EjectEnemyBallFromGripper extends ParallelRaceGroup {
	public EjectEnemyBallFromGripper(){
		addCommands(
				new WaitCommand(2),
				new ReverseRoller(),
				new ReverseFunnel()
		);
	}
}
