package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class ShootAndGo extends SequentialCommandGroup {
	public ShootAndGo(PIDObject pid, double power, double movementTime){
		addCommands(
				new ParallelRaceGroup(
						new ParallelCommandGroup(
								new ShooterByRPM(pid, power),
								new InsertByConstants(power)
						),
						new WaitCommand(5)
				),
				new ParallelRaceGroup(
						new WaitCommand(movementTime),
						new RobotDotMove()
				)
		);
	}
}
