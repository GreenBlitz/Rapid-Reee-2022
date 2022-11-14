package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class MoveBallUntilClick extends ParallelDeadlineGroup {

	public MoveBallUntilClick() {
		super(new WaitUntilCommand(() -> DigitalInputMap.getInstance().getValue(0)), new RunRoller(),new RunFunnel());
		}

}