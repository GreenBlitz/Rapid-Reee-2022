package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class EjectFromShooter extends ParallelRaceGroup {
	public EjectFromShooter(){
		addCommands(
				new WaitUntilCommand(() -> DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.Funnel.MACRO_SWITCH_PORT)),
				new ReverseRunRoller(),
				new RunRoller()
		);
	}
}
