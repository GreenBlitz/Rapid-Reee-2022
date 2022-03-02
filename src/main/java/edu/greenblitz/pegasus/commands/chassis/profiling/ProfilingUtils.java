package edu.greenblitz.pegasus.commands.chassis.profiling;

import org.greenblitz.motion.base.Vector2D;

public class ProfilingUtils {

    public static double[] trasnformInputs(double leftVel, double rightVel, double angle, double mult) {
        return new double[]{leftVel * mult, rightVel * mult, angle * mult};
    }

    public static Vector2D flipToBackwards(Vector2D input, boolean isReverse) {

        if (isReverse) {
            input = input.scale(-1);
        }


        return input;

    }

    public static Vector2D Clamp(Vector2D vals, double maxPower) {
        vals.setX(maxPower * clamp(vals.getX()));
        vals.setY(maxPower * clamp(vals.getY()));
        return vals;
    }

    private static double clamp(double in) {
        return Math.copySign(
                Math.min(Math.abs(in), 1),
                in);
    }

}
