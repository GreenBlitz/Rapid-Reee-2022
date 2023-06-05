package edu.greenblitz.pegasus.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.greenblitz.pegasus.utils.cords.Point;
import edu.greenblitz.pegasus.utils.motors.GBSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

public class Hood extends GBSubsystem {
	GBSparkMax hoodMotor;
	DigitalInput microSwitch;

	private double targetAngle;
	
	private static Hood instance;
	public static Hood getInstance(){
		if (instance == null){
			instance = new Hood();
		}
		return instance;
	}
	private Hood(){
		this.hoodMotor = new GBSparkMax(RobotMap.Pegasus.Shooter.Hood.MOTOR_ID, CANSparkMaxLowLevel.MotorType.kBrushless);
		this.microSwitch = new DigitalInput(RobotMap.Pegasus.Shooter.Hood.LIMIT_SWITCH);
		targetAngle = 0;
	}
	
	public double getAngle(){
		return hoodMotor.getEncoder().getPosition();
	}
	
	public boolean isPressed(){
		return microSwitch.get();
	}
	
	public void setHoodAngle(double angle){
		targetAngle = angle;
		hoodMotor.getPIDController().setReference(angle, CANSparkMax.ControlType.kPosition);
	}


	@Override
	public void periodic() {

		Point target = RobotMap.GameField.SHOOTING_TARGET_POINT;

		double distanceFromTarget = Math.sqrt(
				Math.pow(target.getX() - SwerveChassis.getInstance().getRobotPose().getX(),2)+
						Math.pow( target.getY() - SwerveChassis.getInstance().getRobotPose().getY(),2)


		);

		distanceFromTarget = Math.abs(distanceFromTarget);
		this.setHoodAngle(RobotMap.Pegasus.Shooter.DISTANCE_TO_SHOOTING.linearlyInterpolate(distanceFromTarget)[0]);
	}

	public boolean isAtAngle (){
		return this.getAngle() - targetAngle < RobotMap.Pegasus.Shooter.Hood.TOLERANCE;
	}

}
