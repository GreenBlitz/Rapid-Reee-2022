package edu.greenblitz.icarus.commands.complexClimb;

public class MoveTurningMotorByConstant extends ComplexClimbCommand {
	private double power;

	public MoveTurningMotorByConstant(double power) {
		super();
		this.power = power;
	}

	@Override
	public void execute() {
		complexClimb.safeMoveTurningMotor(power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		complexClimb.safeMoveTurningMotor(0);
	}
}
