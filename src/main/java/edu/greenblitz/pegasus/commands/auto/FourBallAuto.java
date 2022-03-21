package edu.greenblitz.pegasus.commands.auto;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.intake.extender.ExtendRoller;
import edu.greenblitz.pegasus.commands.intake.extender.RetractRoller;
import edu.greenblitz.pegasus.commands.intake.roller.RunRoller;
import edu.greenblitz.pegasus.commands.shifter.ToPower;
import edu.greenblitz.pegasus.commands.shifter.ToSpeed;
import edu.greenblitz.pegasus.commands.shooter.DoubleShoot;
import edu.greenblitz.pegasus.commands.shooter.ShooterByRPM;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import org.greenblitz.motion.pid.PIDObject;

public class FourBallAuto extends SequentialCommandGroup {
	private static final double FIRST_DISTANCE = -1.5;
	private static final double DISTANCE_TO_SHOOT = 1.5;
	private static final double RPM_SHOOTING = 4000;
	private static final double RPM_SHOOTING_2 = 4200;

	private static final double SECOND_DISTANCE = 0.0;
	private static final double ANGLE = Math.toRadians(7);

	public static final PIDObject LIN_OBJECT = new PIDObject(0.5,0,0.2,0);
	public static final PIDObject LIN_OBJECT_ANG = new PIDObject(0.1,0.0000001,0,0);
	public static final PIDObject ANG_OBJECT = new PIDObject(0.4,0,0,0);
	
	private long tStart;


	public FourBallAuto(){
		addCommands(
				new ToSpeed(),
				new ExtendRoller(),
				new ParallelDeadlineGroup(
						new ParallelCommandGroup(
								new MoveFunnelUntilClick(),
								new SequentialCommandGroup(
										new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, FIRST_DISTANCE),
										new WaitCommand(0.2)
								)
						),
						new RunRoller()
//							new ClimbMoveToPosition(ClimbState.MID_GAME),
				),
				new ParallelDeadlineGroup(
						new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, DISTANCE_TO_SHOOT),
						new RunRoller(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING)
				),
				new ToPower(),
				new ParallelRaceGroup(
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING),
						new MoveAngleByPID(ANG_OBJECT, -Math.toRadians(15))
				),
				new DoubleShoot(RPM_SHOOTING),
				new MoveAngleByPID(ANG_OBJECT, ANGLE),
				new ToSpeed(),
				new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -4.5),
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(
								new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, -1.3){
									@Override
									public void initialize() {
										this.angle = chassis.getAngle();
										this.pidControllerLinear.configure(chassis.getMeters(), -1.3,-0.2, 0.2, 0);
										this.pidControllerAngular.configure(chassis.getAngle(), 0,-0.2, 0.2, 0);
										this.startingDistance = chassis.getMeters();
									}
								},
								new WaitCommand(1)
						),
						new MoveFunnelUntilClick(),
						new RunRoller()
//							new ClimbMoveToPosition(ClimbState.MID_GAME),
				),

				new ParallelDeadlineGroup(
						new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, 4.5),
						new RunRoller().withTimeout(1)
				),
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(
								new MoveLinearByPID(LIN_OBJECT, LIN_OBJECT_ANG, 0.8){
									@Override
									public void initialize() {
										this.angle = chassis.getAngle();
										this.pidControllerLinear.configure(chassis.getMeters(), 0.8,-0.2, 0.2, 0);
										this.pidControllerAngular.configure(chassis.getAngle(), 0,-0.2, 0.2, 0);
										this.startingDistance = chassis.getMeters();
									}
								},
								new WaitCommand(0.2)
						),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING_2)
				),
				new ParallelDeadlineGroup(
						new MoveAngleByPID(ANG_OBJECT,-Math.toRadians(15)),
						new ToPower(),
						new ShooterByRPM(RobotMap.Pegasus.Shooter.ShooterMotor.pid, RobotMap.Pegasus.Shooter.ShooterMotor.iZone, RPM_SHOOTING_2)
				),
				new DoubleShoot(RPM_SHOOTING),
				new RetractRoller()
				);
	}
	
	@Override
	public void initialize() {
		super.initialize();
		this.tStart = System.currentTimeMillis();
		
	}
	
	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		
		SmartDashboard.putNumber("time elapsed", (System.currentTimeMillis() - this.tStart)/1000.0);
	}
}
