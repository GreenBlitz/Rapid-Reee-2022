package edu.greenblitz.pegasus.commands.multiSystem;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Chassis;
import edu.greenblitz.pegasus.subsystems.Climb;

public class CoastWhileClimb extends GBCommand {

	public CoastWhileClimb(){
		require(Chassis.getInstance());
	}

	@Override
	public void initialize() {
		super.initialize();
		Chassis.getInstance().toCoast();
//		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
	}

	@Override
	public void end(boolean interrupted) {
		Chassis.getInstance().toBrake();
//		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
	}
}
