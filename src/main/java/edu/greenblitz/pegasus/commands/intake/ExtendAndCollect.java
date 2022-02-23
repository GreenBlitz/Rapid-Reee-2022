package edu.greenblitz.pegasus.commands.intake;

import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ExtendAndCollect extends ParallelCommandGroup {
    public ExtendAndCollect(double power){
        addCommands(new ExtendRoller(), new RollByConstant(power));
    }
}
