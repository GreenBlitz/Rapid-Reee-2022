//package edu.greenblitz.pegasus.subsystems;
//
//
//import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushed.GBBrushedMotor;
//import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushed.TalonSRX.GBTalonSRX;
//import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushed.TalonSRX.TalonSRXFactory;
//import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motors.brushless.AbstractMotor;
//import edu.greenblitz.pegasus.RobotMap;
//
//public class Roller extends Intake {
//
//	private final GBBrushedMotor rollerMotor;
//
//	private static Roller instance;
//
//	public static Roller getInstance() {
//		if (instance==null)
//		{instance = new Roller();}
//		return instance;
//	}
//
//	private Roller() {
//		rollerMotor = new TalonSRXFactory().generate(RobotMap.Pegasus.Intake.Motors.ROLLER_PORT);
//		rollerMotor.setInverted(RobotMap.Pegasus.Intake.Motors.IS_REVERSED);
//		rollerMotor.setIdleMode(AbstractMotor.IdleMode.Coast);
//	}
//
//	public void setPower(double power){
//		rollerMotor.setPower(power);
//	}
//
//}