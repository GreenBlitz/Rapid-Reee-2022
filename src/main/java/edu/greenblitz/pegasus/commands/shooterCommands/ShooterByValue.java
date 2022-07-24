package edu.greenblitz.pegasus.commands.shooterCommands;

public class ShooterByValue extends ShooterCommand {
	private double value;
	public ShooterByValue(double value){
		this.value = value;
	}

	@Override
	public void initialize() {
		super.initialize();

	}

	@Override
	public void execute() {
		super.execute();
		shooter.setPower(value);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void end(boolean interrupted) {
		shooter.setPower(0);
	}
}
