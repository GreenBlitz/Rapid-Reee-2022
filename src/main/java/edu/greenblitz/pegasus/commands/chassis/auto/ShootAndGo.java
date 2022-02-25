package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class ShootAndGo extends SequentialCommandGroup {
	public ShootAndGo(double movementTime){
		addCommands(
				new ParallelRaceGroup(
						new WaitCommand(1),
						new ShootByConstant(0.7)
				),
				new ParallelRaceGroup(
						new WaitCommand(4),
						new ShootByConstant(0.7),
						new InsertByConstants(0.8)
				),
				new ParallelRaceGroup(
						new WaitCommand(movementTime),
						new RobotDotMove()
				)
		);
	}
}
