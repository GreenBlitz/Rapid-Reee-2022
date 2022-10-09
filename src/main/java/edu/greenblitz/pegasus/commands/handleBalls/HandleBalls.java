package edu.greenblitz.pegasus.commands.handleBalls;

import edu.greenblitz.pegasus.commands.funnel.ReverseRunFunnel;
import edu.greenblitz.pegasus.commands.funnel.RunFunnel;
import edu.greenblitz.pegasus.commands.intake.IntakeCommand;
import edu.greenblitz.pegasus.commands.intake.roller.ReverseRunRoller;
import edu.greenblitz.pegasus.commands.shooter.ShooterEvacuate;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.utils.DigitalInputMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class HandleBalls extends IntakeCommand {


	/**
	 * this command checks if an enemy ball is trying to enter the Funnel,
	 * and if so it is rejecting it from entering the funnel system using a
	 * color sensor to check the color
	 *
	 * @see com.revrobotics.ColorSensorV3 colorSensor.
	 * @see Indexing Indexing Subsystem
	 */

	private final Indexing index;
	private boolean isBallInFunnel;

	public HandleBalls() {
		this.index = Indexing.getInstance();
		isBallInFunnel = false;
		require(Indexing.getInstance());
	}

	@Override
	public void execute() {
		SmartDashboard.putBoolean("isBallInFunnel", isBallInFunnel);
		SmartDashboard.putBoolean("isEnemyBallUnSensor", index.isEnemyBallInSensor());
		SmartDashboard.putBoolean("isMacroSwitch", DigitalInputMap.getInstance().getValue(0));

		if (index.isTeamsBallInSensor()) {
			//if our team's ball is in front of the sensor activate the boolean
			isBallInFunnel = true;
		}
		if (DigitalInputMap.getInstance().getValue(0)) {
			//if the ball got to the macroSwitch then disable the boolean
			isBallInFunnel = false;
		}

		if (index.isEnemyBallInSensor()) {
			if (DigitalInputMap.getInstance().getValue(0) || isBallInFunnel) {
				isBallInFunnel = false;
				SmartDashboard.putBoolean("isEvacuatingFromShooter", false);
				//back direction
				new ParallelDeadlineGroup(
						new WaitCommand(0.5),
						new ReverseRunRoller(),
						new ReverseRunFunnel().raceWith(new WaitCommand(0.2))
				).andThen(new RunFunnel().until(() -> DigitalInputMap.getInstance().getValue(0))).schedule(false);
			} else {
				//shooter evacuation
				SmartDashboard.putBoolean("isEvacuatingFromShooter", true);
				new ShooterEvacuate().raceWith(new WaitCommand(5)).schedule(false);

			}
		}
	}
}
