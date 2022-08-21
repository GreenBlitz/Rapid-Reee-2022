package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FullClimb extends SequentialCommandGroup {
	public FullClimb(SmartJoystick joystick) {
		addRequirements(RobotContainer.getInstance().getClimb(), RobotContainer.getInstance().getClimb().getRail(), RobotContainer.getInstance().getClimb().getTurning());
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


