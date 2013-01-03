package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseDiagonalMoveChecker extends BaseMoveChecker {

	protected BaseDiagonalMoveChecker(Board board) {
		super(board);
	}

	@Override
	public int getNeighbourIndex(Cell cell, Player player) {
		return 0;
	}

	protected int getNeighbourIndex(final Cell cell, final Player player, final boolean isBottom) {
		int cellIndex = cell.getIndex();
		int currentNeighbour = 1;

		if ((isBottom && !canMoveBottom(cellIndex)) || (!isBottom && !canMoveTop(cellIndex))) {
			return -1;
		}

		cellIndex = incrementIndex(cellIndex, isBottom);

		while (!isDiagonalEnd(cellIndex)) {
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
				return cellIndex;
			} else if (isStoppingSearch(player, currentNeighbour, currentCell)) {
				return -1;
			}
			cellIndex = incrementIndex(cellIndex, isBottom);
			++currentNeighbour;
		}

		Cell currentCell = board.get(cellIndex);
		if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
			return cellIndex;
		}
		return -1;
	}

	protected abstract boolean canMoveTop(final int cellIndex);

	protected abstract boolean canMoveBottom(final int cellIndex);

	protected abstract int incrementIndex(final int cellIndex, final boolean isBottom);

	protected abstract boolean isDiagonalEnd(final int cellIndex);

}
