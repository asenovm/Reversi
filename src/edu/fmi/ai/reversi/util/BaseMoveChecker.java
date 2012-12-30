package edu.fmi.ai.reversi.util;

import java.util.Map;

import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseMoveChecker {

	protected Map<Integer, Cell> board;

	protected BaseMoveChecker(final Map<Integer, Cell> board) {
		this.board = board;
	}

	protected boolean isStoppingSearch(final Player forPlayer, int i,
			final Cell currentCell) {
		return currentCell.isEmpty()
				|| (currentCell.isOwnedBy(forPlayer) && i == 1);
	}

	protected boolean isClosestNeighbour(final Player forPlayer, int i,
			final Cell currentCell) {
		return currentCell.isOwnedBy(forPlayer) && i > 1;
	}

	public abstract boolean isMovePermitted(final Cell cell,
			final Player forPlayer);

}
