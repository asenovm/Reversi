package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseDiagonalMoveChecker extends VerticalMoveChecker {

	protected BaseDiagonalMoveChecker(Board board) {
		super(board);
	}

	@Override
	protected int getNeighbourIndex(final Cell cell, final Player player,
			final boolean isMinusDirection, final boolean isStoppingSearch) {
		int cellIndex = cell.getIndex();
		int currentNeighbour = 1;

		if (!canMove(isMinusDirection, cellIndex)) {
			return -1;
		}

		cellIndex = incrementIndex(cellIndex, isMinusDirection);

		while (!isDiagonalEnd(cellIndex)) {
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
				return cellIndex;
			} else if (isStoppingSearch && isStoppingSearch(player, currentNeighbour, currentCell)) {
				return -1;
			}
			cellIndex = incrementIndex(cellIndex, isMinusDirection);
			++currentNeighbour;
		}

		Cell currentCell = board.get(cellIndex);
		if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
			return cellIndex;
		}
		return -1;
	}

	private boolean canMove(final boolean isMinusDirection, int cellIndex) {
		return !((!isMinusDirection && !canMoveBottom(cellIndex)) || (isMinusDirection && !canMoveTop(cellIndex)));
	}

	protected abstract boolean canMoveTop(final int cellIndex);

	protected abstract boolean canMoveBottom(final int cellIndex);

	protected abstract boolean isDiagonalEnd(final int cellIndex);

}
