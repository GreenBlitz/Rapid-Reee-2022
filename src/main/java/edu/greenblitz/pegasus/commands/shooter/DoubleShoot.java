package edu.greenblitz.pegasus.commands.shooter;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Funnel;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Intake;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.greenblitz.pegasus.commands.multiSystem.MoveBallUntilClick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

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
						new ShooterByRPM(Shooter.ShooterMotor.pid, Shooter.ShooterMotor.iZone, RPM1),
						new WaitCommand(3)
				),
				new ParallelRaceGroup(
						new WaitCommand(0.2),
						new MoveBallUntilClick()
				),
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(Shooter.ShooterMotor.pid, Shooter.ShooterMotor.iZone, RPM2),
						new WaitCommand(3)
				),
				new WaitCommand(0.2)

		);

	}

	public DoubleShoot(double RPM) {
		this(RPM, RPM);
	}

	public DoubleShoot() {
		this(Shooter.ShooterMotor.RPM);
	}

	@Override
	public void initialize() {
		edu.greenblitz.pegasus.subsystems.Shooter.getInstance().setIdleMode(CANSparkMax.IdleMode.kCoast);
		super.initialize();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		edu.greenblitz.pegasus.subsystems.Shooter.getInstance().setSpeedByPID(0);
	}
}
