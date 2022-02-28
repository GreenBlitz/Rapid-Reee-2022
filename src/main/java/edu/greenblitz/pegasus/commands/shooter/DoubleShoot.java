package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.pegasus.RobotMap.Pegasus.Funnel;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Intake;
import edu.greenblitz.pegasus.RobotMap.Pegasus.Shooter;
import edu.greenblitz.pegasus.commands.funnel.InsertIntoShooter;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.greenblitz.motion.pid.PIDObject;

public class DoubleShoot extends SequentialCommandGroup {
	
	double RPM1;
	double RPM2;
	static PIDObject PID = Shooter.ShooterMotor.pid;
	
	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
		
		addCommands(
				new ParallelDeadlineGroup(new InsertIntoShooter(Funnel.DEFAULT_POWER, Intake.Motors.DEFAULT_POWER), new ShooterByRPM(Shooter.ShooterMotor.pid, RPM1)),
				new ParallelDeadlineGroup(new WaitCommand(0.1),new ShooterByRPM(Shooter.ShooterMotor.pid, RPM2)),
				new ParallelRaceGroup(new InsertIntoShooter(Funnel.DEFAULT_POWER, Intake.Motors.DEFAULT_POWER), new ShooterByRPM(Shooter.ShooterMotor.pid, RPM2), new WaitCommand(1.4999999999999999999999999999999) )
		);
	}
	
	public DoubleShoot(double RPM) {
		this(RPM, RPM);
	}
}
