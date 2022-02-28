package edu.greenblitz.pegasus.commands.chassis.auto;

import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.commands.shooter.ShooterByPID;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class TwoBallsAuto extends SequentialCommandGroup {
	public TwoBallsAuto(PIDObject pid, double target, double movementTime, double power){
		addCommands(
				new ParallelRaceGroup(
						new WaitCommand(movementTime),
						new RollByConstant(power),
						new RobotDotMove(-0.1)
				),
				new ParallelRaceGroup(
						new WaitCommand(movementTime),
						new RobotDotMove()
				),
				new ShooterByPID(pid, target)
		);
	}
}
