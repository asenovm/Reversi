package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class SecondaryDiagonalMoveChecker extends BaseMoveChecker {

	protected SecondaryDiagonalMoveChecker(Board board) {
		super(board);
	}

	public int getTopNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, false);
	}

	public int getBottomNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, true);
	}

	@Override
	public int getNeighbourIndex(final Cell cell, final Player player) {
		return Math.max(getNeighbourIndex(cell, player, true),
				getNeighbourIndex(cell, player, false));
	}

	private int getNeighbourIndex(final Cell cell, final Player player, final boolean isBottom) {
		int cellIndex = cell.getIndex();
		int currentNeighbour = 1;

		if ((isBottom && !canMoveBottom(cellIndex)) || (!isBottom && !canMoveTop(cellIndex))) {
			return -1;
		}

		cellIndex = incrementSecondaryIndex(cellIndex, isBottom);

		while (!isDiagonalEnd(cellIndex)) {
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
				return cellIndex;
			} else if (isStoppingSearch(player, currentNeighbour, currentCell)) {
				return -1;
			}
			cellIndex = incrementSecondaryIndex(cellIndex, isBottom);
			++currentNeighbour;
		}

		Cell currentCell = board.get(cellIndex);
		if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
			return cellIndex;
		}
		return -1;
	}

	private int incrementSecondaryIndex(final int cellIndex, final boolean isBottom) {
		return isBottom ? getSecondaryBottom(cellIndex) : getSecondaryTop(cellIndex);
	}

	private int getSecondaryBottom(final int cellIndex) {
		return cellIndex + Game.BOARD_COLUMN_COUNT - 1;
	}

	private int getSecondaryTop(final int cellIndex) {
		return cellIndex - Game.BOARD_COLUMN_COUNT + 1;
	}

	private boolean isDiagonalEnd(final int cellIndex) {
		return (cellIndex % 8 == 0 || cellIndex / 8 == 0 || cellIndex % 8 == 7 || cellIndex / 8 == 7)
				&& cellIndex != 0 && cellIndex != 63;
	}

	private boolean canMoveBottom(final int cellIndex) {
		return !(cellIndex % 8 == 0 || cellIndex / 8 == 7);
	}

	private boolean canMoveTop(final int cellIndex) {
		return !(cellIndex / 8 == 0 || cellIndex % 8 == 7);
	}
}
