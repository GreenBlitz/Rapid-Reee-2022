package edu.greenblitz.pegasus.commands.funnel;

import edu.greenblitz.gblib.command.GBCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class InsertUntil extends SequentialCommandGroup {

	public InsertUntil(@NotNull FunnelCommand insertionMethod, @NotNull Supplier<Boolean> endCondition) {

		if (insertionMethod.isFinished()) {
			throw new RuntimeException("Insertion method must never finish");
		}

		GBCommand endConditionCommand = new GBCommand() {
			@Override
			public boolean isFinished() {
				return endCondition.get();
			}
		};

		addCommands(new ParallelRaceGroup(insertionMethod, endConditionCommand), new InsertByConstants(0));
	}

}
