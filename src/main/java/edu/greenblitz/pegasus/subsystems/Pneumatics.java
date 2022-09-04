package edu.greenblitz.pegasus.subsystems;

import edu.greenblitz.gblib.subsystems.GBSubsystem;
import edu.greenblitz.pegasus.RobotMap;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Pneumatics extends GBSubsystem {

	private final Compressor m_compressor;

	protected Pneumatics() {
		m_compressor = new Compressor(RobotMap.Pegasus.Pneumatics.PCM.PCM_ID, PneumaticsModuleType.CTREPCM);

	}


	public double getPressure() {
		return m_compressor.getPressure();
	}

	public void setCompressor(boolean compress) {
		if (compress) {
			m_compressor.enableDigital();
		} else {
			m_compressor.disable();
		}

	}

	public boolean isEnabled() {
		return m_compressor.enabled();
	}

	public void reset() {
		setCompressor(false);
	}

	@Override
	public void periodic() {
		super.periodic();
	}
}
