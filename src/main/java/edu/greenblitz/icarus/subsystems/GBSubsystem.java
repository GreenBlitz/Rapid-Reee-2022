package edu.greenblitz.icarus.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class GBSubsystem implements Subsystem {
	
	private final Map<String, Object> tablesToData = new HashMap<>();
	protected NetworkTable table;
	
	public GBSubsystem() {
		table = NetworkTableInstance.getDefault().getTable(this.getClass().getSimpleName());
		CommandScheduler.getInstance().registerSubsystem(this);
	}
	
	public synchronized void putData(String key, Object data) {
		Object sddata = tablesToData.get(key);
		if (sddata == null || sddata != data) {
			tablesToData.put(key, data);
			NetworkTable dataTable = table.getSubTable(key);
			dataTable.getEntry(".name").setString(key);
		}
		
	}


	public synchronized Object getData(String key) {
		Object data = tablesToData.get(key);
		if (data == null) {
			throw new IllegalArgumentException(key);
		} else {
			return data;
		}
	}
	
	public NetworkTableEntry getEntry(String key) {
		return table.getEntry(key);
	}
	
	public boolean containsKey(String key) {
		return table.containsKey(key);
	}
	
	public Set<String> getKeys(int types) {
		return table.getKeys(types);
	}
	
	public Set<String> getKeys() {
		return table.getKeys();
	}
	
	public void setPersistent(String key) {
		getEntry(key).setPersistent();
	}
	
	public void clearPersistent(String key) {
		getEntry(key).clearPersistent();
	}
	
	public boolean isPersistent(String key) {
		return getEntry(key).isPersistent();
	}
	
	public void setFlags(String key, int flags) {
		getEntry(key).setFlags(flags);
	}
	
	public void clearFlags(String key, int flags) {
		getEntry(key).clearFlags(flags);
	}
	
	public int getFlags(String key) {
		return getEntry(key).getFlags();
	}
	
	public void delete(String key) {
		table.delete(key);
	}
	
	public boolean putBoolean(String key, boolean value) {
		return getEntry(key).setBoolean(value);
	}
	
	public boolean setDefaultBoolean(String key, boolean defaultValue) {
		return getEntry(key).setDefaultBoolean(defaultValue);
	}
	
	public boolean getBoolean(String key, boolean defaultValue) {
		return getEntry(key).getBoolean(defaultValue);
	}
	
	public boolean putNumber(String key, double value) {
		return getEntry(key).setDouble(value);
	}
	
	public boolean setDefaultNumber(String key, double defaultValue) {
		return getEntry(key).setDefaultDouble(defaultValue);
	}
	
	public double getNumber(String key, double defaultValue) {
		return getEntry(key).getDouble(defaultValue);
	}
	
	public boolean putString(String key, String value) {
		return getEntry(key).setString(value);
	}
	
	public boolean setDefaultString(String key, String defaultValue) {
		return getEntry(key).setDefaultString(defaultValue);
	}
	
	public String getString(String key, String defaultValue) {
		return getEntry(key).getString(defaultValue);
	}
	
	public boolean putBooleanArray(String key, boolean[] value) {
		return getEntry(key).setBooleanArray(value);
	}
	
	public boolean putBooleanArray(String key, Boolean[] value) {
		return getEntry(key).setBooleanArray(value);
	}
	
	public boolean setDefaultBooleanArray(String key, boolean[] defaultValue) {
		return getEntry(key).setDefaultBooleanArray(defaultValue);
	}
	
	public boolean setDefaultBooleanArray(String key, Boolean[] defaultValue) {
		return getEntry(key).setDefaultBooleanArray(defaultValue);
	}
	
	public boolean[] getBooleanArray(String key, boolean[] defaultValue) {
		return getEntry(key).getBooleanArray(defaultValue);
	}
	
	public Boolean[] getBooleanArray(String key, Boolean[] defaultValue) {
		return getEntry(key).getBooleanArray(defaultValue);
	}
	
	public boolean putNumberArray(String key, double[] value) {
		return getEntry(key).setDoubleArray(value);
	}
	
	public boolean putNumberArray(String key, Double[] value) {
		return getEntry(key).setNumberArray(value);
	}
	
	public boolean setDefaultNumberArray(String key, double[] defaultValue) {
		return getEntry(key).setDefaultDoubleArray(defaultValue);
	}
	
	public boolean setDefaultNumberArray(String key, Double[] defaultValue) {
		return getEntry(key).setDefaultNumberArray(defaultValue);
	}
	
	public double[] getNumberArray(String key, double[] defaultValue) {
		return getEntry(key).getDoubleArray(defaultValue);
	}
	
	public Double[] getNumberArray(String key, Double[] defaultValue) {
		return getEntry(key).getDoubleArray(defaultValue);
	}
	
	public boolean putStringArray(String key, String[] value) {
		return getEntry(key).setStringArray(value);
	}
	
	public boolean setDefaultStringArray(String key, String[] defaultValue) {
		return getEntry(key).setDefaultStringArray(defaultValue);
	}
	
	public String[] getStringArray(String key, String[] defaultValue) {
		return getEntry(key).getStringArray(defaultValue);
	}
	
	public boolean putRaw(String key, byte[] value) {
		return getEntry(key).setRaw(value);
	}
	
	public boolean putRaw(String key, ByteBuffer value, int len) {
		return getEntry(key).setRaw(value, len);
	}
	
	public boolean setDefaultRaw(String key, byte[] defaultValue) {
		return getEntry(key).setDefaultRaw(defaultValue);
	}
	
	public byte[] getRaw(String key, byte[] defaultValue) {
		return getEntry(key).getRaw(defaultValue);
	}
	
	@Override
	public void periodic() {
		Command curr = getCurrentCommand();
		putString("currentCommand", curr == null ? "" : curr.getName());
	}
}
