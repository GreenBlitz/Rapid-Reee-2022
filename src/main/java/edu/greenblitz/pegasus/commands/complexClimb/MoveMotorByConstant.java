package edu.greenblitz.pegasus.commands.complexClimb;

public class MoveMotorByConstant extends ComplexClimbCommand {
	private double power;

	public MoveMotorByConstant(double power) {
		super();
		this.power = power;
	}

	@Override
	public void execute() {
		complexClimb.moveCurrentMotor(power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		complexClimb.moveCurrentMotor(0);
	}
}
