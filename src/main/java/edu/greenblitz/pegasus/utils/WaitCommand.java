package edu.greenblitz.pegasus.utils;

import edu.greenblitz.GBLib.src.main.java.edu.greenblitz.gblib.base.GBCommand;

public class WaitCommand extends GBCommand {

	private final double timeout;
	private final long tStart;


	public WaitCommand(double timeout) {
		this.timeout = timeout; // Wait period in milliseconds.
		this.tStart = System.currentTimeMillis();
	}

	public boolean isFinished() {
		return System.currentTimeMillis() - tStart >= timeout;
	}
}