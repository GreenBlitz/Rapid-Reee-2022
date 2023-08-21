package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.subsystems.Shooter;

public class RollFunnel extends GBCommand {

    @Override
    public void execute() {
        Shooter.getInstance().setFunnelPower();
        //todo set the power here           ^
    }

    @Override
    public void end(boolean interrupted) {
        Shooter.getInstance().setFunnelPower(0);
    }
}
