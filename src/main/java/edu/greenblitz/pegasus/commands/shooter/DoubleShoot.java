package edu.greenblitz.pegasus.commands.shooter;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Funnel;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Intake;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.multiSystem.InsertIntoShooter;
import edu.wpi.first.wpilibj2.command.*;

public class DoubleShoot extends SequentialCommandGroup {

	private double RPM1;
	private double RPM2;

	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
		addCommands(
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(Shooter.ShooterMotor.pid, Shooter.ShooterMotor.iZone, RPM1),
						new WaitCommand(2.5)
				),
				new ParallelRaceGroup(new WaitCommand(0.2), new RunFunnel()),
				new ParallelRaceGroup(
						new InsertIntoShooter(),
						new ShooterByRPM(Shooter.ShooterMotor.pid, Shooter.ShooterMotor.iZone, RPM2),
						new WaitCommand(1.7)
				),
				new ParallelRaceGroup(new WaitCommand(0.2), new RunFunnel())

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
