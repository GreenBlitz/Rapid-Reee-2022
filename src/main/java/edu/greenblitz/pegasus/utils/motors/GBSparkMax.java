package edu.greenblitz.pegasus.utils.motors;

import com.revrobotics.*;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.utils.PIDObject;

public class GBSparkMax extends CANSparkMax {


    /**
     * Create a new object to control a SPARK MAX motor Controller
     *
     * @param deviceId The device ID.
     * @param type     The motor type connected to the controller. Brushless motor wires must be connected
     *                 to their matching colors and the hall sensor must be plugged in. Brushed motors must be
     *                 connected to the Red and Black terminals only.
     */
    public GBSparkMax(int deviceId, MotorType type) {
        super(deviceId, type);
    }
//    public void config (SparkMaxConfObject conf){
//        super.setSmartCurrentLimit(conf.getCurrentLimit());
//        configPID(conf.getPidObject());
//        setGearRatio(conf.getGearRatio());
//        super.setClosedLoopRampRate(conf.getRampRate());
//        super.setOpenLoopRampRate(conf.getRampRate());
//        super.setInverted(conf.isInverted());
//
//    }

    public void setGearRatio (double gearRatio){
        //returning in radians
        super.getEncoder().setPositionConversionFactor(gearRatio);
        super.getEncoder().setVelocityConversionFactor(gearRatio);

    }

    public void configPID (PIDObject pidObject){
        super.getPIDController().setP(pidObject.getKp());
        super.getPIDController().setI(pidObject.getKi());
        super.getPIDController().setD(pidObject.getKd());
        super.getPIDController().setFF(pidObject.getKf());
        super.getPIDController().setIZone(pidObject.getIZone());
        super.getPIDController().setOutputRange(-pidObject.getMaxPower(), pidObject.getMaxPower());
    }



    /**
     * inner conf class
     * */

    public static class SparkMaxConfObject{
        private PIDObject pidObject = new PIDObject(1,0,0);

        public int getCurrentLimit() {
            return currentLimit;
        }


        public double getRampRate() {
            return rampRate;
        }


        public boolean isInverted() {
            return inverted;
        }

        public double getGearRatio() {
            return gearRatio;
        }

        private int currentLimit = 0;
        private double rampRate = 0;
        private boolean inverted = false;
        private double gearRatio = 1;

        public SparkMaxConfObject (){

        }

        public SparkMaxConfObject withPID (PIDObject pidObject){
            this.pidObject = pidObject;
            return this;
        }

        public SparkMaxConfObject withCurrentLimit(int currentLimit){
            this.currentLimit = currentLimit;
            return this;
        }

        public SparkMaxConfObject withRampRate(double rampRate){
            this.rampRate = rampRate;
            return this;
        }

        public SparkMaxConfObject withInverted(Boolean inverted){
            this.inverted = inverted;
            return this;
        }

        public SparkMaxConfObject withGearRatio (double gearRatio){
            this.gearRatio = gearRatio;
            return this;
        }


        public PIDObject getPidObject() {
            return pidObject;
        }
    }

}
