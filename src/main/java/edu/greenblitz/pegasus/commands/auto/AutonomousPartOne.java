package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.commands.shooter.ShootByConstant;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonomousPartOne extends SequentialCommandGroup {


	private double distance;
	private double power;



	public AutonomousPartOne(double distance, double power) {
		super();
		this.distance = distance;
		this.power = power;
	}

	@Override
	public void initialize() {
		addCommands(new MoveStraightByPID(distance), new ShootByConstant(power));
	}
}


