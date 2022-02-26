package edu.greenblitz.pegasus.commands.colorSensor;


import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.subsystems.ColorSensor;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RejectWrongBalls extends ColorSensorCommand {
	ColorSensor.AllianceColor myColor;

	public static final Double ROLLER_POWER = -0.8; //both should be negative
	static public final double FUNNEL_POWER = -0.8;
	static final public double ACTIVATION_TIME = 3.0;

	@Override
	public void initialize() {
		this.myColor = colorSensor.getPerceivedColor();
}

	@Override
	public void execute() {
		if (myColor == ColorSensor.AllianceColor.OTHER) {
			this.myColor = colorSensor.getPerceivedColor();
		}
		if (colorSensor.getPerceivedColor() != myColor)
			new ParallelRaceGroup(
					new WaitCommand(ACTIVATION_TIME),
					new InsertByConstants(FUNNEL_POWER),
					new RollByConstant(ROLLER_POWER)
			);
	}
}
