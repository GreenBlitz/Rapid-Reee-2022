package edu.greenblitz.pegasus.commands.swerve;




import edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.greenblitz.gblib.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveLin extends SwerveCommand{
	
	private double angle;
	private double power;
	
	private PIDObject pidObject;
	
	public MoveLin(double angleInRotations, double power, PIDObject pidObject){
		this.angle = angleInRotations;
		this.power = power;
		this.pidObject = pidObject;
	}
	
	@Override
	public void initialize() {
		swerve.configPID(this.pidObject);
	}

	@Override
	public void execute() {
		swerve.moveChassisLin(angle, power);
		swerve.moveChassisLin(angle,power);
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();

	}
}
