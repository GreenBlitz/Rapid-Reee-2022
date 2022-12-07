package edu.greenblitz.pegasus.utils.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXPIDSetConfiguration;
import edu.greenblitz.pegasus.utils.PIDObject;

public class GBFalcon extends TalonFX {

    /**
     * Constructor
     *
     * @param deviceNumber [0,62]
     */
    public GBFalcon(int deviceNumber) {
        super(deviceNumber);
    }

    /**
     * configs the motor settings using FalconConfObject
     *
     * @param conf configObject, uses builder
     */
    public void config (GBFalcon.FalconConfObject conf){
        super.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, conf.getCurrentLimit(), conf.getCurrentLimit(), 0));
        super.configClosedloopRamp(conf.getRampRate());
        super.configOpenloopRamp(conf.getRampRate());
        super.setInverted(conf.isInverted());
        super.configSelectedFeedbackCoefficient(conf.getConversionFactor());
    }

    private void configPID(PIDObject pidObject){
        super.config_kP(0, pidObject.getKp());
        super.config_kI(0, pidObject.getKi());
        super.config_kD(0, pidObject.getKd());
        super.config_kF(0, pidObject.getKf());
        super.config_IntegralZone(0, pidObject.getIZone());
        super.configClosedLoopPeakOutput(0,pidObject.getMaxPower());
    }


    public static class FalconConfObject {
        private PIDObject pidObject = new PIDObject(1, 0, 0);
        private int currentLimit = 0;
        private double rampRate = 0;
        private boolean inverted = false;
        private double ConversionFactor;
        public FalconConfObject() {

        }

        public int getCurrentLimit() {
            return currentLimit;
        }

        public double getRampRate() {
            return rampRate;
        }

        public boolean isInverted() {
            return inverted;
        }

        public double getConversionFactor() {
            return ConversionFactor;
        }

        public FalconConfObject withConversionFactor (double velocityConversionFactor){
            this.ConversionFactor = velocityConversionFactor;
            return this;
        }

        public FalconConfObject withPID (PIDObject pidObject){
            this.pidObject = pidObject;
            return this;
        }

        public FalconConfObject withCurrentLimit(int currentLimit){
            this.currentLimit = currentLimit;
            return this;
        }

        public FalconConfObject withRampRate(double rampRate){
            this.rampRate = rampRate;
            return this;
        }

        public FalconConfObject withInverted(Boolean inverted){
            this.inverted = inverted;
            return this;
        }

        public PIDObject getPidObject() {
            return pidObject;
        }


    }
}
