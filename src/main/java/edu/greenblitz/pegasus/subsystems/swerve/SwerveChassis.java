package edu.greenblitz.pegasus.subsystems.swerve;

import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.utils.GBMath;
import edu.greenblitz.pegasus.utils.PigeonGyro;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveChassis extends GBSubsystem {
	
	private final SwerveModule frontRight, frontLeft, backRight, backLeft;
	//	private final PigeonGyro pigeonGyro;
	private final PigeonGyro pigeonGyro;
	private final SwerveDriveOdometry localizer;
	private final SwerveDriveKinematics kinematics;
	
	
	public enum Module {
		FRONT_LEFT,
		FRONT_RIGHT,
		BACK_LEFT,
		BACK_RIGHT
	}
	
	public SwerveChassis() {

		this.frontLeft = new KazaSwerveModule(
				RobotMap.Pegasus.Swerve.Module1.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module1.linMotorID,
				RobotMap.Pegasus.Swerve.Module1.AbsoluteEncoderID,
				RobotMap.Pegasus.Swerve.Module1.INVERTED
		);
		
		this.frontRight = new KazaSwerveModule(
				RobotMap.Pegasus.Swerve.Module2.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module2.linMotorID,
				RobotMap.Pegasus.Swerve.Module2.AbsoluteEncoderID,
				RobotMap.Pegasus.Swerve.Module2.INVERTED
		);
		
		this.backLeft = new KazaSwerveModule(
				RobotMap.Pegasus.Swerve.Module3.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module3.linMotorID,
				RobotMap.Pegasus.Swerve.Module3.AbsoluteEncoderID,
				RobotMap.Pegasus.Swerve.Module3.INVERTED
		);
		
		this.backRight = new KazaSwerveModule(
				RobotMap.Pegasus.Swerve.Module4.SteerMotorID,
				RobotMap.Pegasus.Swerve.Module4.linMotorID,
				RobotMap.Pegasus.Swerve.Module4.AbsoluteEncoderID,
				RobotMap.Pegasus.Swerve.Module4.INVERTED
		);
		
		this.pigeonGyro = new PigeonGyro(RobotMap.Pegasus.gyro.pigeonID);
		
		this.kinematics = new SwerveDriveKinematics(
				RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates
		);
		this.localizer = new SwerveDriveOdometry(
				this.kinematics,
				new Rotation2d(this.getChassisAngle()),
				RobotMap.Pegasus.Swerve.initialRobotPosition
		);
	}
	
	
	private static SwerveChassis instance;
	
	public static SwerveChassis getInstance() {
		if (instance == null) {
			instance = new SwerveChassis();
		}
		return instance;
	}
	
	@Override
	public void periodic() {
		localizer.update(new Rotation2d(getChassisAngle()),
				frontLeft.getModuleState(), frontRight.getModuleState(),
				backLeft.getModuleState(), backRight.getModuleState());
	}
	
	/**
	 * @return returns the swerve module based on its name
	 */
	private SwerveModule getModule(Module module) {
		switch (module) {
			case BACK_LEFT:
				return backLeft;
			case BACK_RIGHT:
				return backRight;
			case FRONT_LEFT:
				return frontLeft;
			case FRONT_RIGHT:
				return frontRight;
		}
		return null;
	}
	
	/**
	 * stops all the modules (power(0))
	 */
	public void stop() {
		frontRight.stop();
		frontLeft.stop();
		backRight.stop();
		backLeft.stop();
	}
	
	/**
	 * resetting all the angle motor's encoders to 0
	 */
	public void resetAllEncoders() {
		getModule(Module.FRONT_LEFT).resetEncoderToValue();
		getModule(Module.FRONT_RIGHT).resetEncoderToValue();
		getModule(Module.BACK_LEFT).resetEncoderToValue();
		getModule(Module.BACK_RIGHT).resetEncoderToValue();
	}


//	public void resetAllEncodersByLamprey() {
//		getModule(Module.FRONT_LEFT).resetEncoderByLamprey();
//		getModule(Module.FRONT_RIGHT).resetEncoderByLamprey();
//		getModule(Module.BACK_LEFT).resetEncoderByLamprey();
//		getModule(Module.BACK_RIGHT).resetEncoderByLamprey();
//
//	}
//
	/**
	 * all code below is self-explanatory - well, after a long time It's maybe not self-explanatory
	 * <p>
	 * ALL IN RADIANS, NOT DEGREES
	 */
	
	/**
	 * get the lamprey value of a specific module
	 */
	public double getModuleLampreyValue(Module module) {
		return getModule(module).getAbsoluteEncoderValue();
	}
	
	//
//	public double getLampreyAngle(Module module) {
//		return getModule(module).getLampreyAngle();
//	}
	public double getModuleAngle(Module module) {
		return getModule(module).getModuleAngle();
	}
	
	/**
	 * make the pigeon (gyro) set this angle to be the angle in radians
	 */
	public void resetChassisAngle(double angInRads) {
		
		pigeonGyro.setYaw(angInRads);
	}
	
	/**
	 * when no parameter is given reset the chassis angle to 0
	 */
	
	public void resetChassisAngle() {
		pigeonGyro.setYaw(0);
	}
	
	
	/**
	 * returns chassis angle in radians
	 */
	public double getChassisAngle() {
		return GBMath.modulo(pigeonGyro.getYaw(), 2 * Math.PI);
	}
	
	/**
	 * get module target angle (radians)
	 */
	public double getTarget(Module module) {//todo make more informative name
		return getModule(module).getTargetAngle();
		
	}
	
	/**
	 * setting module states to all 4 modules
	 */
	public void setModuleStates(SwerveModuleState[] states) {
		setModuleStateForModule(Module.FRONT_LEFT,
				SwerveModuleState.optimize(states[0], new Rotation2d(getModuleAngle(Module.FRONT_LEFT))));
		setModuleStateForModule(Module.FRONT_RIGHT,
				SwerveModuleState.optimize(states[1], new Rotation2d(getModuleAngle(Module.FRONT_RIGHT))));
		setModuleStateForModule(Module.BACK_LEFT,
				SwerveModuleState.optimize(states[2], new Rotation2d(getModuleAngle(Module.BACK_LEFT))));
		setModuleStateForModule(Module.BACK_RIGHT,
				SwerveModuleState.optimize(states[3], new Rotation2d(getModuleAngle(Module.BACK_RIGHT))));
		
		
	}
	
	public void moveByChassisSpeeds(double forwardSpeed, double leftwardSpeed, double angSpeed, double currentAng) {
		ChassisSpeeds chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(
				forwardSpeed,
				leftwardSpeed,
				angSpeed,
				Rotation2d.fromDegrees(Math.toDegrees(currentAng)));
		SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
		setModuleStates(states);
	}
	
	public ChassisSpeeds getChassisSpeeds(){
		return kinematics.toChassisSpeeds(getModuleState(Module.FRONT_LEFT),
				getModuleState(Module.FRONT_RIGHT),
				getModuleState(Module.BACK_LEFT),
				getModuleState(Module.BACK_RIGHT));
	}
	
	public SwerveDriveKinematics getKinematics() {
		return this.kinematics;
	}
	
	public SwerveDriveOdometry getLocalizer() {
		return this.localizer;
	}
	
	public Pose2d getLocation() {
		return this.localizer.getPoseMeters();
	}
	
	public void resetLocalizer() {
		localizer.resetPosition(new Pose2d(), new Rotation2d());
	}
	
	public PigeonGyro getPigeonGyro() {
		return pigeonGyro;
	}
	
	/**
	 * moving a single module by module state
	 */
	private void setModuleStateForModule(Module module, SwerveModuleState state) {
		getModule(module).setModuleState(state);
	}
	
	
	/**
	 * moving the chassis linear by angle (radians) and percentOutput [-1, 1]
	 */
	public void moveChassisLin(double angle, double percentOutput) {
		for (Module module : Module.values()) {
			getModule(module).rotateToAngle(angle);
			getModule(module).setLinPowerOnlyForCalibrations(percentOutput);
		}
	}
	
	/**
	 * rotates chassis by percentOutput [-1, 1]
	 */
	public void rotateChassisByPower(double percentOutput) {
		for (int i = 0; i < Module.values().length; i++) {
			Translation2d translationFromCenter = RobotMap.Pegasus.Swerve.SwerveLocationsInSwerveKinematicsCoordinates[i];
			getModule(Module.values()[i]).rotateToAngle(Math.atan2(translationFromCenter.getY(), translationFromCenter.getX()) + Math.PI * 0.5);
			getModule(Module.values()[i]).setLinPowerOnlyForCalibrations(percentOutput);
		}
	}
	
	
	/**
	 * for calibration purposes
	 */
	public void rotateModuleByPower(Module module, double power) {
		getModule(module).setRotPowerOnlyForCalibrations(power);
	}
	
	
	public SwerveModuleState getModuleState(Module module) {
		return getModule(module).getModuleState();
	}
	
}
