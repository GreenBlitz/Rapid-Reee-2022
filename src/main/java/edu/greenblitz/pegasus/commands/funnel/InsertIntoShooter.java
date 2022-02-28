package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj2.command.*;

public class InsertIntoShooter extends SequentialCommandGroup {

	public InsertIntoShooter(double pushConst, double rollConst) {
// todo: make Shooter.isPreparedToShoot work.
		addCommands(
				new ParallelDeadlineGroup(//activates both roller and funnel until the ball is at macro switch
						new WaitUntilCommand(() -> DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.Funnel.MACRO_SWITCH_PORT)),
						new InsertByConstants(pushConst),
						new RollByConstant(rollConst)
				),
				
				//waits until the shooter is ready
				// todo new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),
				new WaitCommand(0.25),
				new ParallelDeadlineGroup(//activates both roller and funnel until ball is no longer at macro switch (was probably propelled)
						new WaitUntilCommand(() -> !DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.Funnel.MACRO_SWITCH_PORT)),
						new InsertByConstants(pushConst),
						new RollByConstant(rollConst)
				)

		);

	}

}
