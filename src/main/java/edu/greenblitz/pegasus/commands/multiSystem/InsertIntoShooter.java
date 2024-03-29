package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.subsystems.shooter.Shooter;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class InsertIntoShooter extends SequentialCommandGroup {

	private double startTime;
	private boolean reported = false;

	// AKA InsertoShooter @tal935
	public InsertIntoShooter() {
		addCommands(
				new MoveBallUntilClick(),

				//waits until the shooter is ready
				new WaitUntilCommand(() -> Shooter.getInstance().isPreparedToShoot()),

				new ParallelDeadlineGroup(//activates both roller and funnel until ball is no longer at macro switch (was probably propelled)
						new WaitUntilCommand(() -> !DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.Funnel.MACRO_SWITCH_PORT)),
						new RunFunnel(),
						new RunRoller()
				));

	}

	@Override
	public void initialize() {
		super.initialize();
		startTime = System.currentTimeMillis() / 1000.0;
	}


	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		reported = false;
	}
}
