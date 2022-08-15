package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.pegasus.commands.climb.Rail.MoveRailToPosition;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.wpi.first.wpilibj2.command.*;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR;
import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rotation.RADIANS_TO_SECOND_BAR;

public class ExtendFully extends SequentialCommandGroup {

	public ExtendFully() {
		addCommands(
				new ParallelCommandGroup(
						new MoveRailToPosition(METERS_TO_SECOND_BAR + 0.15) {
							@Override
							public void execute() {
								climb.safeMoveRailMotor(-1);
							}
						},

						new MoveTurningToAngle(RADIANS_TO_SECOND_BAR + Math.toRadians(12))
				),
				new ParallelCommandGroup(
						new MoveRailToPosition(METERS_TO_SECOND_BAR) {
							@Override
							public void execute() {
								climb.safeMoveRailMotor(-1);
							}
						},
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> RobotContainer.getInstance().getClimb().getLoc() < 0.03),
								new MoveTurningToAngle(RADIANS_TO_SECOND_BAR),
								new ScheduleCommand(new WaitCommand(1) {
									@Override
									public void execute() {
										super.execute();
										RobotContainer.getInstance().getClimb().safeMoveTurningMotor(-0.1);
									}

									@Override
									public void end(boolean interrupted) {
										super.end(interrupted);
										RobotContainer.getInstance().getClimb().safeMoveTurningMotor(0);
									}
								})

						)
				)
		);
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		RobotContainer.getInstance().getClimb().setTurningMotorIdle(AbstractMotor.IdleMode.Brake);
	}
}