package edu.greenblitz.pegasus.utils;

import com.ctre.phoenix.sensors.PigeonIMU;
import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.utils.GBMath;

public class PigeonGyro extends PigeonIMU {
	//fixme noam - reminder for myself. if pigeon not working problem is prob here;

	public PigeonGyro (int id){
		super(id);
	}

	double yawOffset = 0.0;
	double pitchOffset = 0.0;
	double rollOffset = 0.0;


	/**
	 * the offset sets himself the current angle + the offset
	 * because idk but we do it like this
	 * */

	public void setYawOffset (double offset){
		yawOffset += offset;
	}

	public void setPitchOffset (double offset){
		pitchOffset += offset;
	}

	public void setRollOffset (double offset){
		rollOffset += offset;
	}


	public void resetYawAngle(double angInDeegres) {//todo make with our reset

		yawOffset += angInDeegres;
		super.setYaw(angInDeegres);
	}


	public double getYawAngle(){
		return GBMath.modulo(Math.toRadians(super.getYaw()) - yawOffset, 2 * Math.PI);
	}

	public double getPitchAngle(){
		return GBMath.modulo(Math.toRadians(super.getYaw()) - pitchOffset, 2 * Math.PI);
	}

	public double getRollAngle(){
		return GBMath.modulo(Math.toRadians(super.getYaw()) - rollOffset, 2 * Math.PI);
	}



}
