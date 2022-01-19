package edu.greenblitz.icarus.commands.intake;

import edu.greenblitz.icarus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.icarus.commands.intake.roller.RollByConstant;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ExtendAndCollect extends ParallelCommandGroup {
    public ExtendAndCollect(double power){
        addCommands(new ExtendRoller(), new RollByConstant(power));
    }
}
