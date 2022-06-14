package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.gblib.subsystems.shooter.ShooterByRPM;
import edu.greenblitz.pegasus.RobotMap;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import org.greenblitz.motion.pid.PIDObject;

public class MoveAndPrepShooterWithoutPID extends ParallelDeadlineGroup {
	public MoveAndPrepShooterWithoutPID(PIDObject ang, double distance, double angle, double power){
		super(
				new MoveLinearWithoutPID(ang, distance, angle, power),
				new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
		);
	}

	public MoveAndPrepShooterWithoutPID(PIDObject ang, double distance, double power){
		this(ang, distance, -10, power);
	}
}
