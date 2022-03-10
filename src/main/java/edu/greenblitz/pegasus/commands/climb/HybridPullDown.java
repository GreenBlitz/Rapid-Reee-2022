package edu.greenblitz.pegasus.commands.climb;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.RailCommand;
import edu.greenblitz.pegasus.commands.climb.Turning.HoldTurning;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class HybridPullDown extends RailCommand {
	
	private SmartJoystick joystick;
	private boolean scheduled = false;
	private boolean coast = false;
	private SequentialCommandGroup turn;
	public HybridPullDown(SmartJoystick joystick){
		super();
		this.joystick = joystick;
		require(Climb.getInstance());
	}
	
	@Override
	public void initialize() {
	}
	
	@Override
	public void execute() {
		super.execute();
		double railMotorPower = joystick.getAxisValue(SmartJoystick.Axis.LEFT_Y);
		climb.safeMoveRailMotor(-railMotorPower);
		if (!coast){
			double turningMotorPower = joystick.getAxisValue(SmartJoystick.Axis.RIGHT_Y);
			climb.safeMoveTurningMotor(turningMotorPower*0.3);
		}
		if (!scheduled && Math.abs(RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR - climb.getLoc()) >0.10){
			climb.setTurningMotorIdle(CANSparkMax.IdleMode.kCoast);
			coast = true;
		}
		if (!scheduled && Math.abs(RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR - climb.getLoc()) > RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_LOC){
			climb.setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
			turn = new SequentialCommandGroup(new MoveTurningToAngle(RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG), new HoldTurning    (RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG));
			turn.schedule();
			scheduled = true;
		}
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		climb.setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
		scheduled = false;
		coast = false;
		
	}
	
	@Override
	public boolean isFinished() {
		return Math.abs((RobotMap.Pegasus.Climb.ClimbMotors.RAIL_LENGTH - RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR) - climb.getLoc()) < RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON;
	}
}
