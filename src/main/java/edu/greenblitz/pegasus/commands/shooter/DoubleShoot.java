package edu.greenblitz.pegasus.commands.shooter;


import edu.greenblitz.gblib.motors.brushless.AbstractMotor;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.greenblitz.pegasus.subsystems.RobotContainer;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class DoubleShoot extends SequentialCommandGroup {

	private final double RPM1;
	private final double RPM2;

	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
		System.out.println("Command Activated");
		addCommands(
				new ParallelRaceGroup(
						new FirstInsertIntoShooter(),
						new ShooterByRPM(edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.pid, RPM1),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> RobotContainer.getInstance().getShooter().isPreparedToShoot()),
								new WaitCommand(1)
						)
				),
				new ParallelRaceGroup(
						new WaitCommand(0.2),
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.pid, RPM2),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> RobotContainer.getInstance().getShooter().isPreparedToShoot()),
								new WaitCommand(1)
						)),
				new WaitCommand(0.2)

		);

	}

	public DoubleShoot(double RPM) {
		this(RPM, RPM);
	}

	public DoubleShoot() {
		this(edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.RPM);
	}

	@Override
	public void initialize() {
		RobotContainer.getInstance().getShooter().setIdleMode(AbstractMotor.IdleMode.Coast);
		super.initialize();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		RobotContainer.getInstance().getShooter().setSpeedByPID(0);
	}
}
