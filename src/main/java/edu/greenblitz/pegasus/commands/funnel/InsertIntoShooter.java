package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class InsertIntoShooter extends SequentialCommandGroup {

	public InsertIntoShooter(double pushConst, double rollConst) {

		addCommands(

				new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),
				new ParallelCommandGroup(

						new PushByConstant(pushConst),
						new RollByConstant(rollConst)

				)

		);

	}

}
