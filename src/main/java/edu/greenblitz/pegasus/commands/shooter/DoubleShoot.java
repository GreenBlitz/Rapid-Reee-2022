package edu.greenblitz.pegasus.commands.shooter;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.motors.AbstractMotor;
import edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.gblib.subsystems.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Funnel;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Intake;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.wpi.first.wpilibj2.command.*;

public class DoubleShoot extends SequentialCommandGroup {

	private double RPM1;
	private double RPM2;

	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
		System.out.println("Command Activated");
		addCommands(
				new ParallelRaceGroup(
						new FirstInsertIntoShooter(),
						new ShooterByRPM(edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.pid, edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM1),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),
								new WaitCommand(1)
						)
				),
				new ParallelRaceGroup(
						new WaitCommand(0.2),
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.pid, edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM2),
						new SequentialCommandGroup(
								new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),
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
		Shooter.getInstance().setIdleMode(AbstractMotor.IdleMode.Coast);
		super.initialize();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		Shooter.getInstance().setSpeedByPID(0);
	}
}
