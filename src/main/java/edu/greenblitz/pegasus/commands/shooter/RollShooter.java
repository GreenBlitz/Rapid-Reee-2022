package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class RollShooter extends GBCommand {

    @Override
    public void execute() {
        Shooter.getInstance().setShooterPower();
        //todo set power here                ^^
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().setShooterPower(0);
    }
}
