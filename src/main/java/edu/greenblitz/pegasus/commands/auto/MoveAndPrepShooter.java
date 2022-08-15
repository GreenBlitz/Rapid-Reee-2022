package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import org.greenblitz.motion.pid.PIDObject;

public class MoveAndPrepShooter extends ParallelDeadlineGroup {
	public MoveAndPrepShooter(PIDObject lin, PIDObject ang, double distance, double angle) {
		super(
				new MoveLinearByPID(lin, ang, distance, angle),
				new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.RPM)
		);
	}

	public MoveAndPrepShooter(PIDObject lin, PIDObject ang, double distance) {
		this(lin, ang, distance, -10);
	}
}
