package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullClimb extends SequentialCommandGroup {
	public FullClimb(SmartJoystick joystick){
		addRequirements(Climb.getInstance(), Climb.getInstance().getRail(), Climb.getInstance().getTurning());
		addCommands(
				new ClimbToSecondBar(),
				new HybridPullDown(joystick));
	}
}

