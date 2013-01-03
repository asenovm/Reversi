package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseMoveChecker {

	protected Board board;

	protected BaseMoveChecker(final Board board) {
		this.board = board;
	}

	protected boolean isStoppingSearch(final Player forPlayer, int currentNeighbour,
			final Cell currentCell) {
		return currentCell.isEmpty() || (currentCell.isOwnedBy(forPlayer) && currentNeighbour == 1);
	}

	protected boolean isClosestNeighbour(final Player forPlayer, int currentNeighbour,
			final Cell currentCell) {
		return currentCell.isOwnedBy(forPlayer) && currentNeighbour > 1;
	}

	public boolean isMovePermitted(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player) > 0;
	}

	public abstract int getNeighbourIndex(final Cell cell, final Player player);

}
