package edu.greenblitz.icarus.commands.funnel;

import edu.greenblitz.icarus.commands.funnel.PushByConstant;
import edu.greenblitz.icarus.commands.intake.roller.RollByConstant;
import edu.greenblitz.icarus.subsystems.Shooter;
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
