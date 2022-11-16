package edu.greenblitz.pegasus.commands.swerve.garbage;




import edu.greenblitz.pegasus.utils.PIDObject;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.swerve.SwerveCommand;

public class MoveLin extends SwerveCommand {
	
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
		swerve.configPID(this.pidObject, RobotMap.Pegasus.Swerve.linPID);
	}

	@Override
	public void execute() {
		swerve.moveChassisLin(angle, power);
	}
	
	@Override
	public void end(boolean interrupted) {
		swerve.stop();

	}
}
