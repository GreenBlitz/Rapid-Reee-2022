package edu.greenblitz.pegasus.commands.shooter;

import edu.greenblitz.gblib.hid.SmartJoystick;
import org.greenblitz.motion.pid.PIDObject;

public class ShootByTrigger extends ShooterByRPM {
	
	private SmartJoystick joystick;
	private SmartJoystick.Axis axis;
	
	public ShootByTrigger(PIDObject obj, double iZone, double target, SmartJoystick joystick, SmartJoystick.Axis axis) {
		super(obj, iZone, target);
		this.joystick = joystick;
		this.axis = axis;
	}
	
	public ShootByTrigger(PIDObject obj, double target, SmartJoystick joystick, SmartJoystick.Axis axis) {
		super(obj, target);
		this.joystick = joystick;
		this.axis = axis;
	}
	
	
	private static final double DEADZONE = 0.1;
	
	@Override
	public void execute() {
		if (joystick.getAxisValue(axis) > DEADZONE) {
			super.execute();
		}
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		shooter.setSpeedByPID(0);
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
}
