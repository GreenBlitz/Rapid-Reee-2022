package edu.greenblitz.pegasus.commands.shooter;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Funnel;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Intake;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter;
import edu.greenblitz.pegasus.commands.funnel.InsertIntoShooter;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DoubleShoot extends SequentialCommandGroup {

	private double RPM1;
	private double RPM2;
	private static final double TRIAL_AND_ERROR = 2750;

	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
		addCommands(
				new ParallelRaceGroup(new InsertIntoShooter(Funnel.DEFAULT_POWER, Intake.POWER), new ShooterByRPM(Shooter.ShooterMotor.pid, Shooter.ShooterMotor.iZone, RPM1), new WaitCommand(3)),
				new WaitCommand(0.1),
				new ParallelRaceGroup(new InsertIntoShooter(Funnel.DEFAULT_POWER, Intake.POWER), new ShooterByRPM(Shooter.ShooterMotor.pid, Shooter.ShooterMotor.iZone, RPM2), new WaitCommand(3))
		);

	}

	public DoubleShoot(double RPM) {
		this(RPM, RPM);
	}

	public DoubleShoot() {
		this(TRIAL_AND_ERROR);
	}

	@Override
	public void initialize() {
		edu.greenblitz.pegasus.subsystems.Shooter.getInstance().setIdleMode(CANSparkMax.IdleMode.kCoast);
		super.initialize();
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		edu.greenblitz.pegasus.subsystems.Shooter.getInstance().setIdleMode(CANSparkMax.IdleMode.kBrake);
		edu.greenblitz.pegasus.subsystems.Shooter.getInstance().setSpeedByPID(0);
	}
}
