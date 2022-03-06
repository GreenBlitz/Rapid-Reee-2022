package edu.greenblitz.pegasus.commands.climb.Turning;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.command.GBCommand;
import edu.greenblitz.pegasus.subsystems.Climb;

public class WhileHeldCoast extends GBCommand {
	@Override
	public void initialize() {
		super.initialize();
		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
		
	}
	
	@Override
	public void end(boolean interrupted) {
		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
	}
}
