package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static edu.greenblitz.pegasus.commands.auto.FourBallAuto.LIN_OBJECT;
import static edu.greenblitz.pegasus.commands.auto.FourBallAuto.LIN_OBJECT_ANG;

public class ThreeBallAuto extends SequentialCommandGroup {
	public ThreeBallAuto(){
		addCommands(
				// Open roller and prepare to go to the first ball
				new ExtendRoller(),
				new ClimbToMidGame(),
				new ToSpeed(),

				// Go to the first
				new ParallelDeadlineGroup(
						new ParallelCommandGroup(
								new MoveFunnelUntilClick(),
								new SequentialCommandGroup(
										new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, FIRST_DISTANCE),
										new WaitCommand(0.2)
								)
						),
						new RunRoller(),
						new SequentialCommandGroup(
								new WaitCommand(0.5),
								new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
						)
				),
		);
	}
}
