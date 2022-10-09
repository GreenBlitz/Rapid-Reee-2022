package edu.greenblitz.pegasus.commands.multiSystem;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.Funnel;
import edu.greenblitz.pegasus.subsystems.Indexing;
import edu.greenblitz.pegasus.subsystems.Intake;
import edu.greenblitz.pegasus.utils.DigitalInputMap;

public class MoveBallUntilClick extends GBCommand {
	private final Funnel funnel;
	private final Intake intake;
	private final Indexing indexing;

	public MoveBallUntilClick() {
		this.funnel = Funnel.getInstance();
		this.intake = Intake.getInstance();
		this.indexing = Indexing.getInstance();
	}

	@Override
	public void execute() {
		funnel.moveMotor();
		intake.moveRoller();
	}

	@Override
	public void end(boolean interrupted) {
		funnel.stopMotor();
		intake.stopRoller();
	}

	@Override
	public boolean isFinished() {
		return DigitalInputMap.getInstance().getValue(RobotMap.Pegasus.DigitalInputMap.MACRO_SWITCH);
	}
}