package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class RollIntake extends GBCommand {

    @Override
    public void execute() {
        Shooter.getInstance().setIntakePower(-0.3);
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().setIntakePower(0);
    }
}
