package edu.greenblitz.pegasus.commands.chassis;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.commands.chassis.ChassisCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Climb;

public class WhileHeldChassisCoast extends ChassisCommand {
	@Override
	public void initialize() {
		super.initialize();
		Chassis.getInstance().toCoast();

	}

	@Override
	public void end(boolean interrupted) {
		Chassis.getInstance().toBrake();
	}
}
