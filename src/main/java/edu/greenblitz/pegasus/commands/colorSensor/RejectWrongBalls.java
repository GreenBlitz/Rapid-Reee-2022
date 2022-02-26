package edu.greenblitz.pegasus.commands.colorSensor;


import edu.greenblitz.pegasus.commands.funnel.InsertByConstants;
import edu.greenblitz.pegasus.commands.intake.roller.RollByConstant;
import edu.greenblitz.pegasus.subsystems.ColorSensor;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class RejectWrongBalls extends ParallelRaceGroup {
	double rollerPower; //both should be negative
	double funnelPower;
	ColorSensor colorSensor;
	String opponentColor;
	double activationTime;

	public RejectWrongBalls(double rollerPower, double funnelPower,double activationTime, String opponentColor) {
		if (rollerPower < 0) {
			this.rollerPower = rollerPower;
		} else this.rollerPower = -rollerPower;
		if (funnelPower < 0) {
			this.funnelPower = funnelPower;
		} else this.rollerPower = -rollerPower;
		if (opponentColor == "RED" || opponentColor == "BLUE") {
			this.opponentColor = opponentColor;
		}
	}

	@Override
	public void execute() {
		if (colorSensor.getPerceivedColor() == opponentColor)
			addCommands(
					new WaitCommand(activationTime),
					new InsertByConstants(funnelPower),
					new RollByConstant(rollerPower)
			);
	}
}
