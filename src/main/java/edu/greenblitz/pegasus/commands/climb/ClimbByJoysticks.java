package edu.greenblitz.pegasus.commands.climb;

import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimbByJoysticks extends ClimbCommand{

	private SmartJoystick joystick;

	public ClimbByJoysticks(SmartJoystick joystick){
		this.joystick = joystick;
	}

	@Override
	public void execute() {
		double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.moveRailMotor(railMotorPower);

		double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
		climb.moveTurningMotor(turningMotorPower);
	}

	@Override
	public void end(boolean interrupted) {
	}
}
