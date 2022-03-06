package edu.greenblitz.pegasus.commands.climb;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import static edu.greenblitz.pegasus.RobotMap.Pegasus.Climb.ClimbConstants.Rail.ff;

public class HybridPullDown extends ClimbCommand{
	
	private SmartJoystick joystick;
	
	public HybridPullDown(SmartJoystick joystick){
		this.joystick = joystick;
	}
	
	@Override
	public void execute() {
		super.execute();
		double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.safeMoveRailMotor(railMotorPower);
		double turningPower = railMotorPower > ff ? 0.03 : 0;
		climb.safeMoveTurningMotor(turningPower);
		if (RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR - climb.getLoc() >0.25){
			climb.setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
		}
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		climb.setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
	}
}
