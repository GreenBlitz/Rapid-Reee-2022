package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.subsystems.Shooter;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj2.command.*;

public class InsertIntoShooter extends SequentialCommandGroup {

	private double startTime;

// AKA InsertoShooter @tal935
	public InsertIntoShooter() {
		addCommands(
				new ParallelDeadlineGroup(//activates both roller and funnel until the ball is at macro switch
						new WaitUntilCommand(() -> DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.Funnel.MACRO_SWITCH_PORT)),
						new RunFunnel(),
						new RunRoller()
				),
				
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
		startTime = System.currentTimeMillis()/1000.0;
	}


	private boolean reported = false;
	@Override
	public void execute() {
		super.execute();
		if(Shooter.getInstance().isPreparedToShoot() && !reported){
			Shooter.getInstance().putNumber("Time To Shoot", System.currentTimeMillis()/1000.0 - startTime);
			reported = true;
		}
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		reported = false;
	}
}
