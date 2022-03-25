package edu.greenblitz.pegasus.commands.climb;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.hid.SmartJoystick;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.climb.Rail.RailByJoystick;
import edu.greenblitz.pegasus.commands.climb.Rail.RailCommand;
import edu.greenblitz.pegasus.commands.climb.Rail.RailToSecondBar;
import edu.greenblitz.pegasus.commands.climb.Turning.HoldTurning;
import edu.greenblitz.pegasus.commands.climb.Turning.MoveTurningToAngle;
import edu.greenblitz.pegasus.commands.climb.Turning.TurningByJoystick;
import edu.greenblitz.pegasus.subsystems.Climb;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class HybridPullDown extends ParallelRaceGroup {

	public HybridPullDown(SmartJoystick joystick){
		super();
		Climb climb = Climb.getInstance();
		addRequirements(Climb.getInstance());
		addCommands(
				new SequentialCommandGroup(
						new ParallelRaceGroup(
								new RailByJoystick(joystick, 0.5),
								new WaitUntilCommand(() -> Math.abs(RailToSecondBar.GOAL - climb.getLoc()) >0.23)
						),
						new ParallelRaceGroup(
								new RailByJoystick(joystick, 0.2),
								new WaitUntilCommand(() -> Math.abs(RailToSecondBar.GOAL - climb.getLoc()) >0.31)
						),
						new ParallelRaceGroup(
								new RailByJoystick(joystick, 0.5),
								new WaitUntilCommand(() -> Math.abs(RailToSecondBar.GOAL - climb.getLoc()) >0.45)
						),
						new RailByJoystick(joystick, 1)
				),
				new SequentialCommandGroup(
						new ParallelRaceGroup(
								new TurningByJoystick(joystick),
								new WaitUntilCommand(() -> Math.abs(RailToSecondBar.GOAL - climb.getLoc()) >0.10)
						),
						new InstantCommand(() -> climb.setTurningMotorIdle(CANSparkMax.IdleMode.kCoast)),
						new WaitUntilCommand(() -> Math.abs(RailToSecondBar.GOAL - climb.getLoc()) > RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_LOC),
						new InstantCommand(() -> climb.setTurningMotorIdle(CANSparkMax.IdleMode.kBrake)),
						new MoveTurningToAngle(RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG),
						new ParallelRaceGroup(
							new WaitUntilCommand(() -> Math.abs(RobotMap.Pegasus.Climb.ClimbMotors.RAIL_LENGTH - RobotMap.Pegasus.Climb.ClimbConstants.Rail.METERS_TO_SECOND_BAR) - climb.getLoc() < RobotMap.Pegasus.Climb.ClimbConstants.Rail.EPSILON),
							new HoldTurning(RobotMap.Pegasus.Climb.SafetyZones.BATTERY_SAFETY_ANG)
						)
				)
		);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		System.out.println("started pulling");
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		Climb.getInstance().setTurningMotorIdle(CANSparkMax.IdleMode.kBrake);
		System.out.println("finished pulling:  " + interrupted);
	}

}
