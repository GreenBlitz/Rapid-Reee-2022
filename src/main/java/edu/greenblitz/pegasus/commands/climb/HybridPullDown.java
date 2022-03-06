package edu.greenblitz.pegasus.commands.climb;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.RailCommand;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;

public class HybridPullDown extends RailCommand {
	
	private SmartJoystick joystick;
	private boolean scheduled = false;
	private MoveTurningToAngle turn;
	
	public HybridPullDown(SmartJoystick joystick){
		this.joystick = joystick;
		require(Climb.getInstance());
	}
	
	@Override
	public void execute() {
		super.execute();
		double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.safeMoveRailMotor(-railMotorPower);
		if (Math.abs(RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR - climb.getLoc()) >0.10){
			climb.setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
		}
		if (!scheduled && Math.abs(RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR - climb.getLoc()) > RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_LOC){
			climb.setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
			turn = new MoveTurningToAngle(RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG);
			turn.schedule(false);
			scheduled = true;
		}
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		climb.setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
		turn.cancel();
		scheduled = false;
	}
	
	@Override
	public boolean isFinished() {
		
		return climb.getLoc() > (RobotMap.Pegasus.Climb.ClimbMotors.RAIL_LENGTH - RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR);
	}
}
