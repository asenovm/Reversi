package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class VerticalMoveChecker extends BaseMoveChecker {

	public VerticalMoveChecker(final Board board) {
		super(board);
	}

	@Override
	public int getNeighbourIndex(Cell cell, Player player) {
		return Math.max(getTopNeighbourIndex(cell, player), getBottomNeighbourIndex(cell, player));
	}

	public int getTopNeighbourIndex(final Cell cell, final Player player) {
		for (int i = 1; i < cell.getY(); ++i) {
			final int currentIndex = cell.getIndex() - i * Game.BOARD_COLUMN_COUNT;
			final Cell currentCell = board.get(currentIndex);
			if (isClosestNeighbour(player, i, currentCell)) {
				return currentIndex;
			} else if (isStoppingSearch(player, i, currentCell)) {
				return -1;
			}
		}
		return -1;
	}

	public int getBottomNeighbourIndex(final Cell moveCell, final Player forPlayer) {
		for (int i = 1; i < Game.BOARD_ROW_COUNT - moveCell.getY(); ++i) {
			final int currentIndex = moveCell.getIndex() + i * Game.BOARD_COLUMN_COUNT;
			final Cell currentCell = board.get(currentIndex);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				return currentIndex;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				return -1;
			}
		}
		return -1;
	}

}
