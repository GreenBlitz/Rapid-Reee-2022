//package edu.greenblitz.pegasus.commands.climb;
//
//import edu.greenblitz.gblib.base.GBCommand;
//import edu.greenblitz.pegasus.subsystems.Climb;
//
//public abstract class ClimbCommand extends GBCommand {
//	protected Climb climb;
//
//	public ClimbCommand() {
//		require(Climb.getInstance());
//		climb = Climb.getInstance();
//		require(climb.getTurning());
//		require(climb.getRail());
//	}
//}
