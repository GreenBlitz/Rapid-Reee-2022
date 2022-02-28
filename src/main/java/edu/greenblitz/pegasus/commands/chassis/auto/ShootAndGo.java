package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.shooter.ShooterByPID;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class ShootAndGo extends SequentialCommandGroup {
	public ShootAndGo(PIDObject pid, double power, double movementTime){
		addCommands(
				new ShooterByPID(pid, power),
				new ParallelRaceGroup(
						new WaitCommand(movementTime),
						new RobotDotMove()
				)
		);
	}
}
