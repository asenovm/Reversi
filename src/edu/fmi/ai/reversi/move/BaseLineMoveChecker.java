package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseLineMoveChecker extends BaseMoveChecker {

	public BaseLineMoveChecker(Board board) {
		super(board);
	}

	@Override
	protected int getNeighbourIndex(Cell cell, Player player) {
		return Math.max(getNeighbourIndex(cell, player, false),
				getNeighbourIndex(cell, player, true));
	}

	protected int getNeighbourIndex(final Cell cell, final Player player,
			final boolean isMinusDirection) {
		int cellIndex = cell.getIndex();
		for (int i = 1; i < getEndIndex(cell, isMinusDirection); ++i) {
			cellIndex = incrementIndex(cellIndex, isMinusDirection);
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, i, currentCell)) {
				return cellIndex;
			} else if (isStoppingSearch(player, i, currentCell)) {
				return -1;
			}
		}
		return -1;
	}

	protected abstract int getEndIndex(final Cell startCell, final boolean isMinusDirection);

}
