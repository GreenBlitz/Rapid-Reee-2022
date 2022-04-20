/**
 * @authors: Tal Frisch, Amir Rimon , Ido Gutman;
 */

package edu.greenblitz.pegasus.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.commands.ShootByPower;
import edu.greenblitz.pegasus.commands.ShootByTrigger;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Shooter extends GBSubsystem {

	private static Shooter instance;
	private CANSparkMax motor;
	public boolean isOn;

	private Shooter() {
		this.motor = new CANSparkMax(RobotMap.Pegasus.Shooter.ShooterMotor.PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
		isOn = false;
	}


	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}
		return instance;
	}

	public void SetPower(double power) {
		motor.set(power);
	}

	public void initDefaultCommand() {
		instance.setDefaultCommand(new ShootByTrigger());
	}

	public void toggle() {
		isOn = !isOn;
	}


	@Override
	public void periodic() {
		if (isOn) {
			new ShootByPower(0.2).schedule();
		} else {
			new ParallelRaceGroup(new WaitCommand(0.1),new ShootByPower(0));
		}
	}
}

