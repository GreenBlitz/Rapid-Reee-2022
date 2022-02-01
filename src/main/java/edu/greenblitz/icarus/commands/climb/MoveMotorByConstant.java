package edu.greenblitz.icarus.commands.climb;

public class MoveMotorByConstant extends ClimbCommand {

	private double power;

	public MoveMotorByConstant(double power) {
		super();
		this.power = power;
	}

	@Override
	public void execute() {
		climb.safeMoveMotor(power);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		climb.safeMoveMotor(0);
	}
}
