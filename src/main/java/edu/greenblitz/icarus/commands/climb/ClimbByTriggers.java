package edu.greenblitz.icarus.commands.climb;
import edu.greenblitz.icarus.subsystems.Climb;

public class ClimbByTriggers extends ClimbCommand {
    private SmartJoystick motorStick;
    private double motorMultiplier;

    public ClimbByTriggers(SmartJoystick motorStick,double motorMult) {
        this.motorStick = motorStick;
        this.motorMultiplier = motorMult;
    }
    @Override
    public void execute() {
        climb.safeMoveMotor(motorMultiplier * (motorStick.getAxisValue(SmartJoystick.Axis.LEFT_TRIGGER) - motorStick.getAxisValue(SmartJoystick.Axis.RIGHT_TRIGGER)));
    }


}
