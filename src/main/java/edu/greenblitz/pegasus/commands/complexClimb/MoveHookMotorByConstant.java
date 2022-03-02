package edu.greenblitz.pegasus.commands.complexClimb;

public class MoveHookMotorByConstant extends ComplexClimbCommand {
	private double power;

	public MoveHookMotorByConstant(double power) {
		super();
		this.power = power;
	}

	@Override
	public void execute() {
		complexClimb.safeMoveHookMotor(power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		complexClimb.safeMoveHookMotor(0);
	}
}
