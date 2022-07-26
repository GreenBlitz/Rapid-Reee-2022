package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FullClimb extends SequentialCommandGroup {
	public FullClimb(SmartJoystick joystick){
		addRequirements(Climb.getInstance(), Climb.getInstance().getRail(), Climb.getInstance().getTurning());
		addCommands(
				new ExtendFully(),
				new HybridPullDown(joystick),
				new InstantCommand(() -> System.out.println("started move back")),
				new MoveRailToPosition(0.50));
	}


	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		System.out.println(interrupted);
	}
}


