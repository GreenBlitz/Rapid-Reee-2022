package edu.greenblitz.pegasus.commands.swerve;

import edu.greenblitz.pegasus.OI;
import edu.greenblitz.pegasus.RobotMap;
import edu.greenblitz.pegasus.subsystems.swerve.SwerveChassis;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.HashMap;

public class CalibrateLampreyByNeo extends SwerveCommand{

	private HashMap<Double, Integer> lampreyToNeoTicks;
	private static final double POWER = 0.02;

	public CalibrateLampreyByNeo(){
		lampreyToNeoTicks = new HashMap<>();
	}

	@Override
	public void initialize() {
		super.initialize();
		swerve.resetAllEncoders();
//		swerve.rotateModuleByPower(SwerveChassis.Module.FRONT_LEFT, POWER);
	}

	@Override
	public void execute() {
		super.execute();
		int currNeoTicks = (int)(swerve.getModuleAngle(SwerveChassis.Module.FRONT_LEFT) *252);
		SmartDashboard.putNumber("curr tick", currNeoTicks);
		if (!lampreyToNeoTicks.containsValue(currNeoTicks)){
			lampreyToNeoTicks.put(swerve.getModuleLampreyVoltage(SwerveChassis.Module.FRONT_LEFT), currNeoTicks);
		}
	}

	@Override
	public boolean isFinished() {
		for (int i = 0; i < 252; i++) {
			if (!lampreyToNeoTicks.containsValue(i) && !OI.getInstance().getMainJoystick().A.get()){
				SmartDashboard.putNumber("last stopped", i);
				return false;
			}
		}
		return true;
	}

	@Override
	public void end(boolean interrupted) {
		super.end(interrupted);
		printMap(lampreyToNeoTicks);
	}

	public static void printMap(HashMap<Double, Integer> map){
		StringBuilder out = new StringBuilder("private HashMap<Double, Double> lampreyToNeoRadians;\nlampreyToNeoRadians = new HashMap<>();");
		for (double key : map.keySet()) {
			out.append("lampreyToNeoRadians.put(").append(key).append(", ").append(map.get(key)).append(");\n");
		}
		System.out.println(out);
	}
}
