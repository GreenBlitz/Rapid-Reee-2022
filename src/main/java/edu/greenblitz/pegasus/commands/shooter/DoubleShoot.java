package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

public class DoubleShoot extends ShooterCommand {

	double RPM1;
	double RPM2;
	static PIDObject PID = RobotMap.Pegasus.Shooter.ShooterMotor.SHOOTER_PID;

	public DoubleShoot(double RPM1, double RPM2) {
		super();
		this.RPM1 = RPM1;
		this.RPM2 = RPM2;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		if (DigitalInputMap.getInstance().getValue(0)){
			(new ParallelRaceGroup(new ShooterByRPM(PID, RPM1) ,new WaitCommand(1))).schedule();
			(new ParallelCommandGroup(new ShooterByRPM(PID, RPM1), new InsertByConstants(0.8))).schedule();
			if (DigitalInputMap.getInstance().getValue(0)){
				(new ParallelCommandGroup(new ShooterByRPM(PID, RPM2), new InsertByConstants(0.8))).schedule();
			}
			else{
				new ParallelRaceGroup(new InsertByConstants(0.8), new GBCommand() {
					@Override
					public boolean isFinished() {
						return DigitalInputMap.getInstance().getValue(0);
					}
				}
				);

			}
		}

	}
}
