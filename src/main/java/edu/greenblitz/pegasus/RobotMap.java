package edu.greenblitz.pegasus;


import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.motion.pid.PIDObject;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import org.greenblitz.motion.interpolation.Dataset;

public class RobotMap {
	public static class Pegasus {
		
		public static class Joystick {
			public static final int MAIN = 0;
			public static final int SECOND = 1;
		}
		
		public static class Intake {
			
			public static final double POWER = 1.0;
			public static final double REVERSE_POWER = -1.0;
			
			public static class Motors {
				public static final int ROLLER_PORT = 6;
				public static final boolean IS_REVERSED = false;
			}
			
			public static class Solenoid {
				public static final int FORWARD_PORT = 1;
				public static final int REVERSE_PORT = 0;
			}
		}
		
		public static class Shooter {
			public static class ShooterMotor {
				public static final int PORT_LEADER = 7;
				public static final boolean LEADER_INVERTED = true; //todo use this




				public static final double RPM = 2350;
				public static final PIDObject pid = new PIDObject(0.0003, 0.0000003, 0).withIZone(300).withMaxPower(0.9)/*.withFF(0.00012)*/; //d1: 0.0001, 0.0000003, 0


				// devided by 60 because the SysID is in RPS and our code is in RPM
				public static final double ks = 0.31979/60; //todo
				public static final double kv = 0.13012/60;
				public static final double ka = 0.017243/60;

				public static final SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(ShooterMotor.ks, ShooterMotor.kv,ShooterMotor.ka);
				public static final double iZone = 0; //TODO: delete this

				public static final Dataset RPM_TO_POWER = new Dataset(2);
				static { //TODO: delete this
					RPM_TO_POWER.addDatapoint(0, new double[]{-0.0000000001});
					RPM_TO_POWER.addDatapoint(346.17146728515627, new double[]{0.1});
					RPM_TO_POWER.addDatapoint(840.5715405273437, new double[]{0.2});
					RPM_TO_POWER.addDatapoint(1352.4570483398438, new double[]{0.3});
					RPM_TO_POWER.addDatapoint(1890.8568017578125, new double[]{0.4});
					RPM_TO_POWER.addDatapoint(2411.9998046875, new double[]{0.5});
					RPM_TO_POWER.addDatapoint(2904.800322265625, new double[]{0.6});
					RPM_TO_POWER.addDatapoint(3518.057314453125, new double[]{0.7});
					RPM_TO_POWER.addDatapoint(4140.91421875, new double[]{0.8});
					RPM_TO_POWER.addDatapoint(4664.115322265625, new double[]{0.9});
					RPM_TO_POWER.addDatapoint(5209.37181640625, new double[]{1.0});
				}
			}
		}
		
		public static class Funnel {
			public static final double POWER = 0.7;
			public static final double REVERSE_POWER = -0.7;
			public static final int MACRO_SWITCH_PORT = 0;
			
			public static class FunnelMotor {
				public static final int MOTOR_PORT = 5;
				public static final boolean IS_REVERSED = true;
			}
		}
		
		public static class Pneumatics {
			public static class PCM {
				public static final int PCM_ID = 22;
				public static final PneumaticsModuleType PCM_TYPE = PneumaticsModuleType.CTREPCM;
			}
			
			public static class PressureSensor {
				public static final int PRESSURE = 3;
			}
		}
		
		public static class DigitalInputMap {
			public static final int MACRO_SWITCH = 0;
		}
		
		public static class Swerve {
			public static final double WHEEL_CIRC = 0.0517 * 2 * Math.PI; //very accurate right now
			
			public static final double ANG_GEAR_RATIO = 6.0;
			public static final double LIN_GEAR_RATIO = 8.0;
			public static final double MAX_VELOCITY = /*3.7*/ 4.5; // m/s //todo
			
			public static final Translation2d[] SwerveLocationsInSwerveKinematicsCoordinates = new Translation2d[]{
					//the WPILib coordinate system is stupid. (x is forwards, y is letwards)
					//the translations are given rotated by 90 degrees clockwise to avoid coordinates system conversion at output
//					new Translation2d(0.3020647,0.25265), fl
//					new Translation2d(-0.3020647,0.25265),fr
//					new Translation2d(0.3020647,-0.25265),bl
//					new Translation2d(-0.3020647,-0.25265)br
					new Translation2d(-0.3020647, -0.25265),
					new Translation2d(-0.3020647, 0.25265),
					new Translation2d(0.3020647, -0.25265),
					new Translation2d(0.3020647, 0.25265)
				
			};
			//TODO: calibrate GOOD PID

			public static final PIDObject angPID = new PIDObject().withKp(0.5).withKd(10).withMaxPower(0.8);
			public static final PIDObject linPID = new PIDObject().withKp(0.0003).withMaxPower(0.5);
			public static final PIDObject rotationPID = new PIDObject().withKp(0.5).withKi(0).withKd(0).withFF(0.1);
			

			public static final double ks = 0.14876;
			public static final double kv = 3.3055;
			public static final double ka = 0.11023;
			
			public static final double KMaxVelocity = 3.7; //todo find real max speed (meters per second)
			public static final double KMMaxAcceleration = 3; //todo find real max acceleration (meters per second ^2)
			public static final double KMaxAngularVelocity = 9.39; //todo find real max speed (radians per second)
			public static final double KMMaxAngularAcceleration = 1; //todo find real max acceleration (radians per second ^2)
			
			public static class Module1 {//front right
				public static final int linMotorID = 11;
				public static final int SteerMotorID = 3;
				public static final int lampryID = 2;
				public static final int MIN_LAMPREY_VAL = 12;
				public static final int MAX_LAMPREY_VAL = 4041;
				public static final boolean INVERTED = false;
			}
			
			public static class Module2 {//front left
				public static final int linMotorID = 10;
				public static final int SteerMotorID = 1;
				public static final int lampryID = 0;
				
				public static final int MIN_LAMPREY_VAL = 22;
				public static final int MAX_LAMPREY_VAL = 4040;
				public static final boolean INVERTED = false;
			}
			
			public static class Module3 {//back right
				public static final int linMotorID = 5;
				public static final int SteerMotorID = 12;
				public static final int lampryID = 3;
				
				public static final int MIN_LAMPREY_VAL = 32;
				public static final int MAX_LAMPREY_VAL = 4021;
				public static final boolean INVERTED = true;
			}
			
			public static class Module4 {//back left
				public static final int linMotorID = 8;
				public static final int SteerMotorID = 2;
				public static final int lampryID = 1;
				public static final int MIN_LAMPREY_VAL = 20;
				public static final int MAX_LAMPREY_VAL = 2646; //todo calibrate in 5v
				public static final boolean INVERTED = true;
			}
		}
		
	}
}